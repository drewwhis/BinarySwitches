package drewwhis.binaryswitches.listeners;

import android.widget.CompoundButton;

import drewwhis.binaryswitches.ui.activities.GameActivity;
import drewwhis.binaryswitches.ui.views.ToggleLabelViewGroup;

/**
 * A listener to update the value of a GameActivity when a ToggleLabelViewGroup changes state.
 */
public class ToggleListener implements CompoundButton.OnCheckedChangeListener {
  private final GameActivity mActivity;
  private final ToggleLabelViewGroup mGroup;

  /**
   * Creates a ToggleListener for a ToggleLabelViewGroup in the context of an activity.
   * @param activity Activity that the listener references.
   * @param group Group to which the listener is applied.
   * @throws IllegalArgumentException Listener and activity cannot be null.
   */
  public ToggleListener(GameActivity activity, ToggleLabelViewGroup group)
      throws IllegalArgumentException {
    if (activity == null || group == null) {
      throw new IllegalArgumentException("Activity and Group cannot be null.");
    }

    mActivity = activity;
    mGroup = group;
  }

  /**
   * Updates the value in the activity with the bit value of the ToggleLabelViewGroup.
   *
   * {@inheritDoc}
   */
  @Override
  public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
    int currentValue = mActivity.getCurrentValue();
    int adjustingValue = mGroup.getBitValue();
    mActivity.setCurrentValue(b ? currentValue + adjustingValue : currentValue - adjustingValue);
  }
}
