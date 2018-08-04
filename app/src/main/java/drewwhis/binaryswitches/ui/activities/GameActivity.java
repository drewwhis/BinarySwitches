package drewwhis.binaryswitches.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

import drewwhis.binaryswitches.R;
import drewwhis.binaryswitches.listeners.ToggleListener;
import drewwhis.binaryswitches.ui.views.ToggleLabelViewGroup;

public class GameActivity extends AppCompatActivity {
  private static final int BYTES = 1;
  private static final int BITS_PER_BYTE = 8;
  private static final int MAX_BOUND = (int) Math.pow(2, BYTES * BITS_PER_BYTE);

  private Button newNumber;
  private Button reset;

  private TextView goal;
  private TextView progress;

  private Random random;
  private int mValue = 0;

  /**
   * Initializes all views and variables.
   * {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);

    if (random == null) {
      random = new Random();
    }

    initializeAllTextViews();

    if (newNumber == null) {
      newNumber = findViewById(R.id.new_number_button);
      newNumber.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          int value = random.nextInt(MAX_BOUND);
          goal.setText(String.format(
              Locale.US,
              getResources().getString(R.string.number_format),
              value));
        }
      });
    }

    final ConstraintLayout topLayout = findViewById(R.id.game_activity_layout);
    ToggleLabelViewGroup toggleLabelViewGroup;
    int topConstrainingViewId = R.id.current_text;

    for (int row = BYTES; row > 0; row--) {
      toggleLabelViewGroup = new ToggleLabelViewGroup(this, row * BITS_PER_BYTE - 1);
      toggleLabelViewGroup.setOnCheckedChangedListener(new ToggleListener(this, toggleLabelViewGroup));

      toggleLabelViewGroup.setId(View.generateViewId());
      topLayout.addView(toggleLabelViewGroup);

      ConstraintSet constraintSet = new ConstraintSet();
      constraintSet.clone(topLayout);
      constraintSet.connect(toggleLabelViewGroup.getId(), ConstraintSet.LEFT, topLayout.getId(), ConstraintSet.LEFT, 16);
      constraintSet.connect(toggleLabelViewGroup.getId(), ConstraintSet.TOP, topConstrainingViewId, ConstraintSet.BOTTOM, 16);
      constraintSet.applyTo(topLayout);

      topConstrainingViewId = toggleLabelViewGroup.getId();

      for (int bit = 2; bit <= 8; bit++) {
        toggleLabelViewGroup = new ToggleLabelViewGroup(this, row * BITS_PER_BYTE - bit);
        toggleLabelViewGroup.setOnCheckedChangedListener(new ToggleListener(this, toggleLabelViewGroup));

        toggleLabelViewGroup.setId(View.generateViewId());
        topLayout.addView(toggleLabelViewGroup);

        constraintSet = new ConstraintSet();
        constraintSet.clone(topLayout);
        constraintSet.connect(toggleLabelViewGroup.getId(), ConstraintSet.LEFT, topConstrainingViewId, ConstraintSet.RIGHT, 0);
        constraintSet.connect(toggleLabelViewGroup.getId(), ConstraintSet.TOP, topConstrainingViewId, ConstraintSet.TOP, 0);
        constraintSet.applyTo(topLayout);

        topConstrainingViewId = toggleLabelViewGroup.getId();
      }
    }

    if (reset == null) {
      reset = findViewById(R.id.reset_button);
      reset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          for (int i = 0; i < topLayout.getChildCount(); i++) {
            if (topLayout.getChildAt(i) instanceof ToggleLabelViewGroup) {
              ((ToggleLabelViewGroup) topLayout.getChildAt(i)).reset();
            }
          }
        }
      });
    }
  }


  /**
   * Initializes each text view.
   * <p>
   * <p>
   * For each text view:
   * <ul>
   * <li>Assigns the text view to a variable.</li>
   * <li>Apply the text changed listener to toast when progress matches goal.</li>
   * </ul>
   * </p>
   */
  private void initializeAllTextViews() {
    if (goal == null) {
      goal = findViewById(R.id.goal_text);
      goal.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          // Do nothing before text changes.
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          String currentGoal = charSequence.toString();
          String currentProgress = progress.getText().toString();

          if (currentGoal.equals(currentProgress)) {
            Toast
                .makeText(getApplicationContext(), "You got it!", Toast.LENGTH_SHORT)
                .show();
          }

        }

        @Override
        public void afterTextChanged(Editable editable) {
          // Do nothing after text changes.
        }
      });
    }

    goal.setTypeface(Typeface.createFromAsset(getAssets(),  "fonts/Segment7Standard.otf"));

    if (progress == null) {
      progress = findViewById(R.id.current_text);
      progress.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          // Do nothing before text changes.
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
          String currentProgress = charSequence.toString();
          String currentGoal = goal.getText().toString();

          if (currentGoal.equals(currentProgress)) {
            Toast
                .makeText(getApplicationContext(), "You got it!", Toast.LENGTH_SHORT)
                .show();
          }

        }

        @Override
        public void afterTextChanged(Editable editable) {
          // Do nothing after text changes.
        }
      });
    }

    progress.setTypeface(Typeface.createFromAsset(getAssets(),  "fonts/Segment7Standard.otf"));
  }

  public int getCurrentValue() {
    return mValue;
  }

  public void setCurrentValue(int newValue) {
    mValue = newValue;
  }

}
