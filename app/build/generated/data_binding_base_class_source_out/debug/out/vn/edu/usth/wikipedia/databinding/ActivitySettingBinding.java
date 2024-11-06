// Generated by view binder compiler. Do not edit!
package vn.edu.usth.wikipedia.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import vn.edu.usth.wikipedia.R;

public final class ActivitySettingBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton closeMoreButton;

  @NonNull
  public final ImageButton donateButton;

  @NonNull
  public final Button donateButtonText;

  @NonNull
  public final FrameLayout fragmentContainer;

  @NonNull
  public final ImageButton languageButton;

  @NonNull
  public final Button languageButtonText;

  @NonNull
  public final ImageButton loginButton;

  @NonNull
  public final Button loginButtonText;

  @NonNull
  public final RelativeLayout settingBar;

  @NonNull
  public final RelativeLayout settingLayout;

  @NonNull
  public final ImageView usthLogo;

  private ActivitySettingBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageButton closeMoreButton, @NonNull ImageButton donateButton,
      @NonNull Button donateButtonText, @NonNull FrameLayout fragmentContainer,
      @NonNull ImageButton languageButton, @NonNull Button languageButtonText,
      @NonNull ImageButton loginButton, @NonNull Button loginButtonText,
      @NonNull RelativeLayout settingBar, @NonNull RelativeLayout settingLayout,
      @NonNull ImageView usthLogo) {
    this.rootView = rootView;
    this.closeMoreButton = closeMoreButton;
    this.donateButton = donateButton;
    this.donateButtonText = donateButtonText;
    this.fragmentContainer = fragmentContainer;
    this.languageButton = languageButton;
    this.languageButtonText = languageButtonText;
    this.loginButton = loginButton;
    this.loginButtonText = loginButtonText;
    this.settingBar = settingBar;
    this.settingLayout = settingLayout;
    this.usthLogo = usthLogo;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySettingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySettingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_setting, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySettingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.close_more_button;
      ImageButton closeMoreButton = ViewBindings.findChildViewById(rootView, id);
      if (closeMoreButton == null) {
        break missingId;
      }

      id = R.id.donate_button;
      ImageButton donateButton = ViewBindings.findChildViewById(rootView, id);
      if (donateButton == null) {
        break missingId;
      }

      id = R.id.donate_button_text;
      Button donateButtonText = ViewBindings.findChildViewById(rootView, id);
      if (donateButtonText == null) {
        break missingId;
      }

      id = R.id.fragment_container;
      FrameLayout fragmentContainer = ViewBindings.findChildViewById(rootView, id);
      if (fragmentContainer == null) {
        break missingId;
      }

      id = R.id.language_button;
      ImageButton languageButton = ViewBindings.findChildViewById(rootView, id);
      if (languageButton == null) {
        break missingId;
      }

      id = R.id.language_button_text;
      Button languageButtonText = ViewBindings.findChildViewById(rootView, id);
      if (languageButtonText == null) {
        break missingId;
      }

      id = R.id.login_button;
      ImageButton loginButton = ViewBindings.findChildViewById(rootView, id);
      if (loginButton == null) {
        break missingId;
      }

      id = R.id.login_button_text;
      Button loginButtonText = ViewBindings.findChildViewById(rootView, id);
      if (loginButtonText == null) {
        break missingId;
      }

      id = R.id.setting_bar;
      RelativeLayout settingBar = ViewBindings.findChildViewById(rootView, id);
      if (settingBar == null) {
        break missingId;
      }

      RelativeLayout settingLayout = (RelativeLayout) rootView;

      id = R.id.usth_logo;
      ImageView usthLogo = ViewBindings.findChildViewById(rootView, id);
      if (usthLogo == null) {
        break missingId;
      }

      return new ActivitySettingBinding((RelativeLayout) rootView, closeMoreButton, donateButton,
          donateButtonText, fragmentContainer, languageButton, languageButtonText, loginButton,
          loginButtonText, settingBar, settingLayout, usthLogo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
