package drewwhis.binaryswitches;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Locale;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
  private static final int TOGGLES = 8;
  private static final int MAX_BOUND = (int) Math.pow(2, TOGGLES);
  private static final String NUMBER_FORMAT = "%d";

  private final ToggleButton[] powers = new ToggleButton[TOGGLES];

  private Button newNumber;
  private Button reset;
  private TextView goal;
  private TextView progress;
  private Random random;
  private int progressValue = 0;

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
    initializeAllPowerToggleButtons();


    if (newNumber == null) {
      newNumber = findViewById(R.id.newNumberButton);
      newNumber.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          int value = random.nextInt(MAX_BOUND);
          goal.setText(String.format(Locale.US, NUMBER_FORMAT, value));
        }
      });
    }

    if (reset == null) {
      reset = findViewById(R.id.resetButton);
      reset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          for (int power = 0; power < TOGGLES; power++) {
            if (powers[power] != null) {
              powers[power].setChecked(false);
            }
          }
        }
      });
    }
  }


  /**
   * Initializes each text view.
   *
   * <p>
   *   For each text view:
   *   <ul>
   *     <li>Assigns the text view to a variable.</li>
   *     <li>Apply the text changed listener to toast when progress matches goal.</li>
   *   </ul>
   * </p>
   */
  private void initializeAllTextViews() {
    if (goal == null) {
      goal = findViewById(R.id.goalText);
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

    if (progress == null) {
      progress = findViewById(R.id.progressText);
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
  }

  /**
   * Initializes all power toggle buttons.
   */
  private void initializeAllPowerToggleButtons() {
    initializePowerToggleButton(R.id.powerButton0, 0);
    initializePowerToggleButton(R.id.powerButton1, 1);
    initializePowerToggleButton(R.id.powerButton2, 2);
    initializePowerToggleButton(R.id.powerButton3, 3);
    initializePowerToggleButton(R.id.powerButton4, 4);
    initializePowerToggleButton(R.id.powerButton5, 5);
    initializePowerToggleButton(R.id.powerButton6, 6);
    initializePowerToggleButton(R.id.powerButton7, 7);
  }

  /**
   * Initializes a specific power toggle button.
   *
   * <p>
   *   For a given toggle button:
   *   <ul>
   *     <li>Assigns the toggle button into the toggle button array.</li>
   *     <li>Toggles it off.</li>
   *     <li>Applies the on checked changed listener to adjust progressValue and progress text.</li>
   *   </ul>
   * </p>
   * @param id The R.id value of the toggle button.
   * @param power The power of 2 represented by the toggle button.
   */
  private void initializePowerToggleButton(int id, final int power) {
    if (power >= 0 && power < powers.length && powers[power] == null) {
      powers[power] = findViewById(id);

      if (powers[power] != null) {
        powers[power].setChecked(false);
        powers[power].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            int addend = (int) Math.pow(2, power);
            progressValue = b ? progressValue + addend : progressValue - addend;
            progress.setText(String.format(Locale.US, NUMBER_FORMAT, progressValue));
          }
        });
      }
    }
  }
}
