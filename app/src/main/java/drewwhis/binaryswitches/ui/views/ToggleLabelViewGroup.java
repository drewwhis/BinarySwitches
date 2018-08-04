package drewwhis.binaryswitches.ui.views;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Locale;

import drewwhis.binaryswitches.R;

public class ToggleLabelViewGroup extends ViewGroup {
  private static final int TOGGLE_INDEX = 0;
  private static final int LABEL_INDEX = 1;

  private final ToggleButton mToggleButton;
  private final TextView mTextView;

  private int mBitIndex;

  public ToggleLabelViewGroup(Context context) throws IllegalArgumentException {
    super(context);

    if (!(getChildAt(TOGGLE_INDEX) instanceof  ToggleButton)
        || !(getChildAt(LABEL_INDEX) instanceof TextView)) {
      throw new IllegalArgumentException("Children elements are not correct.");
    }

    mBitIndex = Integer.MIN_VALUE;
    mToggleButton = (ToggleButton) getChildAt(TOGGLE_INDEX);
    mTextView = (TextView) getChildAt(LABEL_INDEX);

    mTextView.setText(
        String.format(
            Locale.US,
            getResources().getString(R.string.number_format),
            getBitValue()));
  }

  public ToggleLabelViewGroup(Context context, int aBitIndex) throws IllegalArgumentException {
    super(context);

    if (!(getChildAt(TOGGLE_INDEX) instanceof  ToggleButton)
        || !(getChildAt(LABEL_INDEX) instanceof TextView)) {
      throw new IllegalArgumentException("Children elements are not correct.");
    }

    mBitIndex = aBitIndex;
    mToggleButton = (ToggleButton) getChildAt(TOGGLE_INDEX);
    mTextView = (TextView) getChildAt(LABEL_INDEX);

    mTextView.setText(
        String.format(
            Locale.US,
            getResources().getString(R.string.number_format),
            getBitValue()));
  }

  public int getBitValue() {
    return (int) Math.pow(2, mBitIndex);
  }

  public int getBitIndex() {
    return mBitIndex;
  }

  public boolean isToggleOn() {
    return mToggleButton.isChecked();
  }

  @Override
  protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
  }
}
