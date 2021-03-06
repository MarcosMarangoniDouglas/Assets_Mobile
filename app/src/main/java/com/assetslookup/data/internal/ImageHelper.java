package com.assetslookup.data.internal;

import android.graphics.Bitmap;

public interface ImageHelper {
  Bitmap convertBase64ToBitmap(String image);
  String convertBitmapToBase64(Bitmap image);
  String convertBitmapToBase64Resized(Bitmap image);
}
