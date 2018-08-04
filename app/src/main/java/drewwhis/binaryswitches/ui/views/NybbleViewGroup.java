package drewwhis.binaryswitches.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import drewwhis.binaryswitches.R;
import drewwhis.binaryswitches.listeners.ToggleListener;
import drewwhis.binaryswitches.ui.activities.GameActivity;

/**
 * ViewGroup to contain four ToggleLabelViewGroups.
 * ViewGroup to represent four bits (a nybble).
 */
public class NybbleViewGroup extends LinearLayout {
  private static final String TAG = NybbleViewGroup.class.getSimpleName();
  private static final int BITS_PER_NYBBLE = 4;

  private final List<ToggleLabelViewGroup> mBits;

  private int mNybbleIndex;

  /**
   * {@inheritDoc}
   * @throws InflateException Could not get inflater.
   */
  public NybbleViewGroup(Context context) throws InflateException {
    this(context, null);
  }

  /**
   * {@inheritDoc}
   * @throws InflateException Could not get inflater.
   */
  public NybbleViewGroup(Context context, @Nullable AttributeSet attrs) throws InflateException {
    this(context, attrs, 0);
  }

  /**
   * {@inheritDoc}
   * @throws InflateException Could not get inflater.
   */
  public NybbleViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) throws InflateException {
    super(context, attrs, defStyleAttr);

    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    if (inflater == null) {
      Log.e(TAG, "Null Inflater.");
      throw new InflateException("Could not get inflater.");
    }

    setOrientation(LinearLayout.HORIZONTAL);
    setGravity(Gravity.CENTER);
    inflater.inflate(R.layout.viewgroup_nybble, this, true);

    List<ToggleLabelViewGroup> tempBits = new ArrayList<>();
    for (int i = 0; i < BITS_PER_NYBBLE; i++) {
      tempBits.add((ToggleLabelViewGroup) getChildAt(i));
    }

    mBits = Collections.unmodifiableList(tempBits);
    mNybbleIndex = 0;
  }

  /**
   * Sets the nybble index of the nybble in the context of an overall number and updates the bits.
   * @param index Nybble index of the nybble in the context of an overall number.
   */
  public void setNybbleIndex(int index) {
    mNybbleIndex = index;
    for (int i = BITS_PER_NYBBLE - 1; i >= 0; i--) {
      mBits.get(BITS_PER_NYBBLE - 1 - i).setBitIndex(BITS_PER_NYBBLE * mNybbleIndex + i);
    }
  }

  /**
   * Applies OnCheckedChangeListeners to the ToggleLabelViewGroups.
   * @param context Activity to which the listeners should reference.
   * @throws IllegalArgumentException Activity cannot be null.
   */
  public void applyOnCheckedChangeListenersTo(GameActivity context) throws IllegalArgumentException {
    if (context == null) {
      throw new IllegalArgumentException("Context cannot be null.");
    }

    for (int i = 0; i < BITS_PER_NYBBLE; i++) {
      ToggleLabelViewGroup bit = mBits.get(i);
      bit.setOnCheckedChangedListener(new ToggleListener(context, bit));
    }
  }

  /**
   * Sets all bits in the nybble to off.
   */
  public void reset() {
    for (ToggleLabelViewGroup toggle : mBits) {
      if (toggle != null) {
        toggle.setToggledState(false);
      }
    }
  }
}
