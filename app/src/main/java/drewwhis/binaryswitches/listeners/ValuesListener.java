package drewwhis.binaryswitches.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Listener to toast the user when two TextViews in the same context have matching text.
 */
public class ValuesListener implements TextWatcher {
  private final TextView mFirstView;
  private final TextView mSecondView;
  private final String mToastText;

  /**
   * Creates a listener for two TextViews that toasts the user when their contents match.
   * @param firstView One of the TextViews to compare.
   * @param secondView One of the TextViews to compare.
   * @param displayText The Toast to display on a match.
   * @throws IllegalArgumentException Views/Display Text cannot be null,View contexts must match.
   */
  public ValuesListener(TextView firstView, TextView secondView, String displayText)
      throws IllegalArgumentException {
    if (firstView == null || secondView == null) {
      throw new IllegalArgumentException("TextViews cannot be null.");
    }

    if (firstView.getContext() != secondView.getContext()) {
      throw new IllegalArgumentException("TextViews must be in the same context.");
    }

    if (displayText == null) {
      throw new IllegalArgumentException("Toasting text cannot be null.");
    }

    mFirstView = firstView;
    mSecondView = secondView;
    mToastText = displayText;
  }

  /**
   * No action is taken before text changed.
   *
   * {@inheritDoc}
   */
  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    // Do nothing before text changes.
  }

  /**
   * Alerts the user if the values in the two views match.
   *
   * {@inheritDoc}
   */
  @Override
  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    String firstString = mFirstView.getText().toString();
    String secondString = mSecondView.getText().toString();

    if (firstString.equals(secondString)) {
      Toast.makeText(mFirstView.getContext(), mToastText, Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * No action is taken after text changed.
   *
   * {@inheritDoc}
   */
  @Override
  public void afterTextChanged(Editable editable) {
    // Do nothing after text changes.
  }
}
