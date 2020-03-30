package com.assetslookup.ui.shared;

import android.content.Context;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.SoundEffectConstants;
import android.widget.EditText;

import com.assetslookup.R;

public class PasswordEditText extends CustomEditText {

  private boolean isTextVisible = false;

  public PasswordEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    inititalize();
  }

  public PasswordEditText(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    inititalize();
  }

  private void inititalize() {
    setDrawableClickListener(new DrawableClickListener() {
      @Override
      public void onClick(DrawablePosition target) {
        playSoundEffect(SoundEffectConstants.CLICK);
        switch (target) {
          case LEFT:
            isTextVisible = !isTextVisible;
            if(isTextVisible) {
              setTransformationMethod(null);
              setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.show_password_clipart), null, null, null);
            } else {
              setTransformationMethod(PasswordTransformationMethod.getInstance());
              setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.hide_password_clipart), null, null, null);
            }
            break;
        }
      }
    });
  }
}
