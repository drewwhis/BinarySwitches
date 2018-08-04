package drewwhis.binaryswitches.ui.activities;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableLong;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import drewwhis.binaryswitches.R;
import drewwhis.binaryswitches.databinding.ActivityGameBinding;
import drewwhis.binaryswitches.listeners.ValuesListener;
import drewwhis.binaryswitches.ui.views.NybbleViewGroup;

public class GameActivity extends AppCompatActivity {
  private static final int BYTES = 5;
  private static final int BITS_PER_BYTE = 8;
  private static final long MAX_BOUND = (long) Math.pow(2, BYTES * BITS_PER_BYTE);

  private Button newNumber;
  private Button reset;

  private TextView goal;
  private TextView progress;

  private ObservableLong mValue = new ObservableLong(0);

  /**
   * Initializes all views and variables.
   *
   * {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityGameBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_game);
    binding.setCurrentValue(mValue);

    if (goal == null) {
      goal = findViewById(R.id.goal_text);
    }

    if (progress == null) {
      progress = findViewById(R.id.current_text);
    }

    ValuesListener textListener
        = new ValuesListener(goal, progress, getResources().getString(R.string.values_match_toast));
    goal.addTextChangedListener(textListener);
    progress.addTextChangedListener(textListener);

    if (newNumber == null) {
      newNumber = findViewById(R.id.new_number_button);
      newNumber.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          long value = Math.abs(ThreadLocalRandom.current().nextLong(MAX_BOUND));
          goal.setText(String.format(
              Locale.US,
              getResources().getString(R.string.number_format),
              value));
        }
      });
    }

    final ConstraintLayout topLayout = findViewById(R.id.game_activity_layout);
    final LinearLayout scrollLayout = findViewById(R.id.nybble_scroll);

    for (int i = 2 * BYTES - 1; i >= 0; i--) {
      NybbleViewGroup nybbleViewGroup = new NybbleViewGroup(this);
      nybbleViewGroup.setNybbleIndex(i);
      nybbleViewGroup.applyOnCheckedChangeListenersTo(this);
      nybbleViewGroup.setId(View.generateViewId());
      scrollLayout.addView(nybbleViewGroup);
    }

    if (reset == null) {
      reset = findViewById(R.id.reset_button);
      reset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          for (int i = 0; i < topLayout.getChildCount(); i++) {
            if (topLayout.getChildAt(i) instanceof NybbleViewGroup) {
              ((NybbleViewGroup) topLayout.getChildAt(i)).reset();
            }
          }
        }
      });
    }
  }

  /**
   * Gets the current user value held by the activity.
   * @return Current user value held by the activity.
   */
  public long getCurrentValue() {
    return mValue.get();
  }

  /**
   * Sets the current user value held by the activity.
   * @param newValue Current user value to be held by the activity.
   */
  public void setCurrentValue(long newValue) {
    mValue.set(newValue);
  }

}
