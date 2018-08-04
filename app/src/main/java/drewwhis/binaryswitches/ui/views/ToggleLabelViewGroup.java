package drewwhis.binaryswitches.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Locale;

import drewwhis.binaryswitches.R;

/**
 * ViewGroup to contain a single ToggleButton above a single TextView.
 * ViewGroup that represents a bit.
 * TextView represents the value of the bit.
 * ToggleButton represents the state of the bit.
 */
public class ToggleLabelViewGroup extends LinearLayout {
  private static final String TAG = ToggleLabelViewGroup.class.getSimpleName();
  private static final int TOGGLE_INDEX = 0;
  private static final int LABEL_INDEX = 1;

  private ToggleButton mToggleButton;
  private TextView mTextView;
  private int mBitIndex;

  /**
   * {@inheritDoc}
   * @throws InflateException Could not get inflater.
   */
  public ToggleLabelViewGroup(Context context) throws InflateException {
    this(context, null);
  }

  /**
   * {@inheritDoc}
   * @throws InflateException Could not get inflater.
   */
  public ToggleLabelViewGroup(Context context, @Nullable AttributeSet attrs) throws InflateException {
    this(context, attrs, 0);
  }

  /**
   * {@inheritDoc}
   * @throws InflateException Could not get inflater.
   */
  public ToggleLabelViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) throws InflateException {
    super(context, attrs, defStyleAttr);

    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    if (inflater == null) {
      Log.e(TAG, "Null Inflater.");
      throw new InflateException("Could not get inflater.");
    }

    setOrientation(LinearLayout.VERTICAL);
    setGravity(Gravity.CENTER);
    inflater.inflate(R.layout.viewgroup_toggle_label, this, true);

    mToggleButton = (ToggleButton) getChildAt(TOGGLE_INDEX);
    mTextView = (TextView) getChildAt(LABEL_INDEX);

    mToggleButton.setChecked(false);
    setBitIndex(0);
  }

  /**
   * Sets the bit index of the bit in the context of an overall number and updates the TextView.
   * @param index Bit index of this bit in the context of an overall number.
   */
  public void setBitIndex(int index) {
    mBitIndex = index;

    mTextView.setText(String.format(
        Locale.US,
        getResources().getString(R.string.number_format),
        getBitValue()));
  }

  /**
   * Gets the value of the bit as a power of two based on it's index.
   * @return Value of the bit as a power of two.
   */
  public long getBitValue() {
    return (long) Math.pow(2, mBitIndex);
  }

  /**
   * Applies the OnCheckedChangeListener to the ToggleButton.
   * @param listener CheckedChangeListener.
   * @throws IllegalArgumentException Listener cannot be null.
   */
  public void setOnCheckedChangedListener(CompoundButton.OnCheckedChangeListener listener) throws IllegalArgumentException {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null.");
    }

    mToggleButton.setOnCheckedChangeListener(listener);
  }

  /**
   * Sets the toggled state of the toggle button.
   * @param toggledState Whether the button should be toggled.
   */
  public void setToggledState(boolean toggledState) {
    mToggleButton.setChecked(toggledState);
  }

}
