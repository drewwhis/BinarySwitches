package drewwhis.binaryswitches.listeners;

import android.widget.CompoundButton;

import drewwhis.binaryswitches.ui.activities.GameActivity;
import drewwhis.binaryswitches.ui.views.ToggleLabelViewGroup;

public class ToggleListener implements CompoundButton.OnCheckedChangeListener {
  private final GameActivity mActivity;
  private final ToggleLabelViewGroup mGroup;

  public ToggleListener(GameActivity activity, ToggleLabelViewGroup group) {
    if (activity == null || group == null) {
      throw new IllegalArgumentException("Activity and Group cannot be null.");
    }

    mActivity = activity;
    mGroup = group;
  }

  @Override
  public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
    int currentValue = mActivity.getCurrentValue();
    int adjustingValue = mGroup.getBitValue();
    mActivity.setCurrentValue(b ? currentValue + adjustingValue : currentValue - adjustingValue);
  }
}
