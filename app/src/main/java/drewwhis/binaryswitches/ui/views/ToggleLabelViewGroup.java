package drewwhis.binaryswitches.ui.views;


import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Locale;

import drewwhis.binaryswitches.R;

public class ToggleLabelViewGroup extends LinearLayout {
  private static final String TAG = ToggleLabelViewGroup.class.getSimpleName();
  private static final int TOGGLE_INDEX = 0;
  private static final int LABEL_INDEX = 1;

  private final ToggleButton mToggleButton;
  private final TextView mTextView;
  private final int mBitIndex;

  public ToggleLabelViewGroup(Context context) {
    super(context);
    mToggleButton = null;
    mTextView = null;
    mBitIndex = 0;
  }

  public ToggleLabelViewGroup(Context context, int bitIndex) throws IllegalArgumentException {
    super(context);

    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    if (inflater == null) {
      Log.e(TAG, "Null Inflater.");
      throw new IllegalArgumentException("Could not get inflater.");
    }

    setOrientation(LinearLayout.VERTICAL);
    setGravity(Gravity.CENTER);
    inflater.inflate(R.layout.viewgroup_toggle_label, this, true);

    mToggleButton = (ToggleButton) getChildAt(TOGGLE_INDEX);
    mTextView = (TextView) getChildAt(LABEL_INDEX);
    mBitIndex = bitIndex;

    mToggleButton.setChecked(false);
    mTextView.setText(String.format(
        Locale.US,
        getResources().getString(R.string.number_format),
        getBitValue()));
  }

  public int getBitIndex() {
    return mBitIndex;
  }

  public int getBitValue() {
    return (int) Math.pow(2, mBitIndex);
  }

  @Override
  protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
    super.onLayout(b, i, i1, i2, i3);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

}
