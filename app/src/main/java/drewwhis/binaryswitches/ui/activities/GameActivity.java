package drewwhis.binaryswitches.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

import drewwhis.binaryswitches.R;
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
      newNumber = findViewById(R.id.newNumberButton);
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
    ConstraintSet constraintSet;

    for (int i = BYTES; i > 0; i--) {
      toggleLabelViewGroup = new ToggleLabelViewGroup(this, BITS_PER_BYTE * BYTES - 1);
      topLayout.addView(toggleLabelViewGroup);

      constraintSet = new ConstraintSet();
      constraintSet.clone(topLayout);
      constraintSet.connect(toggleLabelViewGroup.getId(), ConstraintSet.LEFT, topLayout.getId(), ConstraintSet.LEFT, 16);
    }

    if (reset == null) {
      reset = findViewById(R.id.resetButton);
      reset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

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

}
