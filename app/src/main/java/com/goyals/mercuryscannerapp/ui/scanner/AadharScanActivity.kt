package com.goyals.mercuryscannerapp.ui.scanner

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.goyals.mercuryscannerapp.R
import com.goyals.mercuryscannerapp.ui.EditFormActivity
import com.goyals.mercuryscannerapp.utils.AppUtils
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class AadharScanActivity : AppCompatActivity() {
  private var previewView: PreviewView? = null
  private var cameraProvider: ProcessCameraProvider? = null
  private var cameraSelector: CameraSelector? = null
  private var lensFacing = CameraSelector.LENS_FACING_BACK
  private var previewUseCase: Preview? = null
  private var analysisUseCase: ImageAnalysis? = null
  private val screenAspectRatio: Int
    get() {
      // Get screen metrics used to setup camera for full screen resolution
      val metrics =
        DisplayMetrics().also { previewView?.display?.getRealMetrics(it) }
      return aspectRatio(metrics.widthPixels, metrics.heightPixels)
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_aadhar_scan)
    setupCamera()
  }

  private fun setupCamera() {
    previewView = findViewById(R.id.preview_view)
    cameraSelector = CameraSelector.Builder()
      .requireLensFacing(lensFacing)
      .build()
    ViewModelProvider(this,
      ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
      CameraXViewModel::class.java).processCameraProvider.observe(this,
      Observer { provider: ProcessCameraProvider? ->
        cameraProvider = provider
        if (isCameraPermissionGranted()) {
          bindCameraUseCases()
        } else {
          ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA_REQUEST)
        }
      })
  }

  private fun bindCameraUseCases() {
    bindPreviewUseCase()
    bindAnalyseUseCase()
  }

  private fun bindPreviewUseCase() {
    if (cameraProvider == null) {
      return
    }
    if (previewUseCase != null) {
      cameraProvider!!.unbind(previewUseCase)
    }

    previewUseCase = Preview.Builder()
      .setTargetAspectRatio(screenAspectRatio)
      .setTargetRotation(previewView!!.display.rotation)
      .build()
    previewUseCase!!.setSurfaceProvider(previewView!!.surfaceProvider)

    try {
      cameraProvider!!.bindToLifecycle(/* lifecycleOwner= */this,
        cameraSelector!!, previewUseCase)
    } catch (illegalStateException: IllegalStateException) {
      Log.e(TAG, illegalStateException.message!!)
    } catch (illegalArgumentException: IllegalArgumentException) {
      Log.e(TAG, illegalArgumentException.message!!)
    }
  }

  private fun bindAnalyseUseCase() {
    // Note that if you know which format of barcode your app is dealing with, detection will be
    // faster to specify the supported barcode formats one by one, e.g.
    //BarcodeScannerOptions.Builder()
    //  .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
    //  .build()
    val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient()

    if (cameraProvider == null) {
      return
    }
    if (analysisUseCase != null) {
      cameraProvider!!.unbind(analysisUseCase)
    }

    analysisUseCase = ImageAnalysis.Builder()
      .setTargetAspectRatio(screenAspectRatio)
      .setTargetRotation(previewView!!.display.rotation)
      .build()
    // Initialize our background executor
    val cameraExecutor = Executors.newSingleThreadExecutor()

    analysisUseCase?.setAnalyzer(cameraExecutor,
      ImageAnalysis.Analyzer { imageProxy ->
        processImageProxy(barcodeScanner, imageProxy)
      })

    try {
      cameraProvider!!.bindToLifecycle(/* lifecycleOwner= */this,
        cameraSelector!!, analysisUseCase)
    } catch (illegalStateException: IllegalStateException) {
      Log.e(TAG, illegalStateException.message!!)
    } catch (illegalArgumentException: IllegalArgumentException) {
      Log.e(TAG, illegalArgumentException.message!!)
    }
  }

  @SuppressLint("UnsafeExperimentalUsageError")
  private fun processImageProxy(barcodeScanner: BarcodeScanner,
    imageProxy: ImageProxy) {
    val inputImage = InputImage.fromMediaImage(imageProxy.image!!,
      imageProxy.imageInfo.rotationDegrees)
    barcodeScanner.process(inputImage)
      .addOnSuccessListener { barcodes ->
        imageProxy.close()
        checkForBarCodeValidations(barcodes)
      }
      .addOnFailureListener {
        Log.e(TAG, it.message!!)
      }
      .addOnCompleteListener {
        Log.e(TAG, "complete$it")
        imageProxy.close()
      }
  }

  private fun aspectRatio(width: Int,
    height: Int): Int {
    val previewRatio = max(width, height).toDouble() / min(width, height)
    if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(
        previewRatio - RATIO_16_9_VALUE)) {
      return AspectRatio.RATIO_4_3
    }
    return AspectRatio.RATIO_16_9
  }

  override fun onRequestPermissionsResult(requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray) {
    if (requestCode == PERMISSION_CAMERA_REQUEST) {
      if (isCameraPermissionGranted()) {
        bindCameraUseCases()
      } else {
        Log.e(TAG, "no camera permission")
      }
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  private fun isCameraPermissionGranted(): Boolean {
    return ContextCompat.checkSelfPermission(baseContext,
      Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
  }

  private fun checkForBarCodeValidations(barcodes: List<Barcode>) {
    if (barcodes.isNotEmpty() && barcodes[0].rawValue!!.length > 100) {
      val rawString = barcodes[0].rawValue!!
      Log.d(TAG, rawString)
      val editFormActivityIntent =
        Intent(this, EditFormActivity::class.java).apply {
          putExtra("aadhar_info",
            AppUtils.convertScanDataToAadharData(rawString))
        }
      startActivity(editFormActivityIntent)
      finish()
    }
  }

  companion object {
    private val TAG = AadharScanActivity::class.java.simpleName
    private const val PERMISSION_CAMERA_REQUEST = 1
    private const val RATIO_4_3_VALUE = 4.0 / 3.0
    private const val RATIO_16_9_VALUE = 16.0 / 9.0
  }
}