// Generated by view binder compiler. Do not edit!
package vn.edu.usth.wikipedia.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import vn.edu.usth.wikipedia.R;

public final class FragmentEditProfileBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageButton closeUserButton;

  @NonNull
  public final Spinner daySpinner;

  @NonNull
  public final EditText editAge;

  @NonNull
  public final EditText editEmail;

  @NonNull
  public final EditText editName;

  @NonNull
  public final EditText editPassword;

  @NonNull
  public final EditText editUsername;

  @NonNull
  public final Spinner monthSpinner;

  @NonNull
  public final Button saveButton;

  @NonNull
  public final RelativeLayout userBar;

  @NonNull
  public final EditText yearInput;

  private FragmentEditProfileBinding(@NonNull LinearLayout rootView,
      @NonNull ImageButton closeUserButton, @NonNull Spinner daySpinner, @NonNull EditText editAge,
      @NonNull EditText editEmail, @NonNull EditText editName, @NonNull EditText editPassword,
      @NonNull EditText editUsername, @NonNull Spinner monthSpinner, @NonNull Button saveButton,
      @NonNull RelativeLayout userBar, @NonNull EditText yearInput) {
    this.rootView = rootView;
    this.closeUserButton = closeUserButton;
    this.daySpinner = daySpinner;
    this.editAge = editAge;
    this.editEmail = editEmail;
    this.editName = editName;
    this.editPassword = editPassword;
    this.editUsername = editUsername;
    this.monthSpinner = monthSpinner;
    this.saveButton = saveButton;
    this.userBar = userBar;
    this.yearInput = yearInput;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentEditProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentEditProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_edit_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentEditProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.close_user_button;
      ImageButton closeUserButton = ViewBindings.findChildViewById(rootView, id);
      if (closeUserButton == null) {
        break missingId;
      }

      id = R.id.day_spinner;
      Spinner daySpinner = ViewBindings.findChildViewById(rootView, id);
      if (daySpinner == null) {
        break missingId;
      }

      id = R.id.edit_age;
      EditText editAge = ViewBindings.findChildViewById(rootView, id);
      if (editAge == null) {
        break missingId;
      }

      id = R.id.edit_email;
      EditText editEmail = ViewBindings.findChildViewById(rootView, id);
      if (editEmail == null) {
        break missingId;
      }

      id = R.id.edit_name;
      EditText editName = ViewBindings.findChildViewById(rootView, id);
      if (editName == null) {
        break missingId;
      }

      id = R.id.edit_password;
      EditText editPassword = ViewBindings.findChildViewById(rootView, id);
      if (editPassword == null) {
        break missingId;
      }

      id = R.id.edit_username;
      EditText editUsername = ViewBindings.findChildViewById(rootView, id);
      if (editUsername == null) {
        break missingId;
      }

      id = R.id.month_spinner;
      Spinner monthSpinner = ViewBindings.findChildViewById(rootView, id);
      if (monthSpinner == null) {
        break missingId;
      }

      id = R.id.save_button;
      Button saveButton = ViewBindings.findChildViewById(rootView, id);
      if (saveButton == null) {
        break missingId;
      }

      id = R.id.user_bar;
      RelativeLayout userBar = ViewBindings.findChildViewById(rootView, id);
      if (userBar == null) {
        break missingId;
      }

      id = R.id.year_input;
      EditText yearInput = ViewBindings.findChildViewById(rootView, id);
      if (yearInput == null) {
        break missingId;
      }

      return new FragmentEditProfileBinding((LinearLayout) rootView, closeUserButton, daySpinner,
          editAge, editEmail, editName, editPassword, editUsername, monthSpinner, saveButton,
          userBar, yearInput);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
