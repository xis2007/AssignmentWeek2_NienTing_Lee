package com.justinlee.assignmentweek2_nienting_lee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private static final String OPERAND_PLUS = "+";
    private static final String OPERAND_MINUS = "-";
    private static final String OPERAND_TIMES = "*";
    private static final String OPERAND_DIVIDE = "/";
    private static final String SIGN_POINT = ".";

    ImageButton mButton0;
    ImageButton mButton1;
    ImageButton mButton2;
    ImageButton mButton3;
    ImageButton mButton4;
    ImageButton mButton5;
    ImageButton mButton6;
    ImageButton mButton7;
    ImageButton mButton8;
    ImageButton mButton9;
    ImageButton mButtonPoint;
    ImageButton mButtonPlus;
    ImageButton mButtonMinus;
    ImageButton mButtonTimes;
    ImageButton mButtonDivide;
    ImageButton mButtonEquals;
    ImageButton mButtonReset;
    ImageButton mButtonChangeSign;
    ImageButton mButtonPercent;

    TextView mShownText;
    TextView mShownResult;

    private static final String NO_ENTERED_EXPRESSION = "";
    private String enteredExpression = NO_ENTERED_EXPRESSION;

    // controllers used to avoid input of consecutive point or operands
    private boolean pointEntered = false;
    private boolean operandEntered = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setOnclickListenersToButtons();

    }


    private void initViews() {
        mButton0 = findViewById(R.id.button_0);
        mButton1 = findViewById(R.id.button_1);
        mButton2 = findViewById(R.id.button_2);
        mButton3 = findViewById(R.id.button_3);
        mButton4 = findViewById(R.id.button_4);
        mButton5 = findViewById(R.id.button_5);
        mButton6 = findViewById(R.id.button_6);
        mButton7 = findViewById(R.id.button_7);
        mButton8 = findViewById(R.id.button_8);
        mButton9 = findViewById(R.id.button_9);
        mButtonPoint = findViewById(R.id.button_point);
        mButtonPlus = findViewById(R.id.button_plus);
        mButtonMinus = findViewById(R.id.button_minus);
        mButtonTimes = findViewById(R.id.button_times);
        mButtonDivide = findViewById(R.id.button_divide);
        mButtonEquals = findViewById(R.id.button_equals);
        mButtonReset = findViewById(R.id.button_reset);
        mButtonChangeSign = findViewById(R.id.button_changesign);
        mButtonPercent = findViewById(R.id.button_percent);

        mShownText = findViewById(R.id.textTyped);
        mShownText.setText("");
        mShownResult = findViewById(R.id.textResult);
        mShownResult.setText("");
    }

    private void setOnclickListenersToButtons() {
        mButton0.setOnClickListener(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);
        mButton9.setOnClickListener(this);
        mButtonPoint.setOnClickListener(this);
        mButtonPlus.setOnClickListener(this);
        mButtonMinus.setOnClickListener(this);
        mButtonTimes.setOnClickListener(this);
        mButtonDivide.setOnClickListener(this);
        mButtonEquals.setOnClickListener(this);
        mButtonReset.setOnClickListener(this);
        mButtonChangeSign.setOnClickListener(this);
        mButtonPercent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();
        switch (buttonId) {
            case R.id.button_0:
                if (!enteredExpression.equals("0")) {
                    enteredExpression += "0";
                    mShownText.setText(enteredExpression);
                }
                break;
            case R.id.button_1:
                appendEnteredNumber("1");
                break;
            case R.id.button_2:
                appendEnteredNumber("2");
                break;
            case R.id.button_3:
                appendEnteredNumber("3");
                break;
            case R.id.button_4:
                appendEnteredNumber("4");
                break;
            case R.id.button_5:
                appendEnteredNumber("5");
                break;
            case R.id.button_6:
                appendEnteredNumber("6");
                break;
            case R.id.button_7:
                appendEnteredNumber("7");
                break;
            case R.id.button_8:
                appendEnteredNumber("8");
                break;
            case R.id.button_9:
                appendEnteredNumber("9");
                break;

            case R.id.button_point:
                appendEnteredOperandOrPoint(SIGN_POINT);
                break;
            case R.id.button_plus:
                appendEnteredOperandOrPoint(OPERAND_PLUS);
                break;
            case R.id.button_minus:
                appendEnteredOperandOrPoint(OPERAND_MINUS);
                break;
            case R.id.button_times:
                appendEnteredOperandOrPoint(OPERAND_TIMES);
                break;
            case R.id.button_divide:
                appendEnteredOperandOrPoint(OPERAND_DIVIDE);
                break;

            case R.id.button_equals:
                if(!enteredExpression.equals("0") && !enteredExpression.equals(NO_ENTERED_EXPRESSION)) {
                    cleanEnteredExpression();
                    showResult(enteredExpression, false);
                }
                break;
            case R.id.button_reset:
                resetEnteredExpression();
                break;

            case R.id.button_changesign:
                calculateResultAndChangeSign();
                break;
            case R.id.button_percent:
                break;

            default:
                break;
        }

//        mShownText.setText(enteredExpression);
    }




    private void cleanEnteredExpression() {
        int stringLen = enteredExpression.length();
        String lastChar = enteredExpression.substring(stringLen - 1);
        switch (lastChar) {
            case OPERAND_PLUS:
            case OPERAND_MINUS:
            case OPERAND_TIMES:
            case OPERAND_DIVIDE:
            case SIGN_POINT:
                enteredExpression.replace(enteredExpression.substring(stringLen - 1), "");
                break;

            default:
                break;
        }
    }

    private void resetEnteredExpression() {
        enteredExpression = NO_ENTERED_EXPRESSION;
        mShownText.setText("");
        mShownResult.setText("");
        allowOperandOrPointToBeEntered();
    }

    private void appendEnteredNumber(String s) {
        enteredExpression += s;
        allowOperandOrPointToBeEntered();
        mShownText.setText(enteredExpression);
    }

    private void allowOperandOrPointToBeEntered() {
        pointEntered = false;
        operandEntered = false;
    }

    private void appendEnteredOperandOrPoint(String s) {
        if (enteredExpression.equals(NO_ENTERED_EXPRESSION)) {
            // enteredExpression is empty means Equals button is just pressed
            // check and see if shownResult has something first
            if(checkWhetherShownResultExists()) {
                // no expressionEntered but ShownResult exists
                // if the entered key is one of the operand, then append it to the enteredExpression for calculation
                switch (s) {
                    case OPERAND_PLUS:
                    case OPERAND_MINUS:
                    case OPERAND_TIMES:
                    case OPERAND_DIVIDE:
                        enteredExpression = mShownResult.getText().toString();
                        enteredExpression += s;
                        mShownText.setText(enteredExpression);
                        operandEntered = true;
                        pointEntered = false;
                        break;

                    default:
                        break;
                }
            } else {
                // no expressionEntered and no ShownResult
                // operands cannot be appended, only when point is entered can it be appended to 0
                switch (s) {
                    case SIGN_POINT:
                        enteredExpression = "0.";
                        operandEntered = false;
                        pointEntered = true;
                        break;

                    default:
                        break;
                }
            }
        } else {
            // if something is in enteredExpression, it means Equaals button is not pressed,
            // use enteredExpression to continue append the Operand or Point
            if ((pointEntered == false) && (operandEntered == false)) {
                enteredExpression += s;
                switch (s) {
                    case OPERAND_PLUS:
                    case OPERAND_MINUS:
                    case OPERAND_TIMES:
                    case OPERAND_DIVIDE:
                        operandEntered = true;
                        pointEntered = false;
                        break;
                    case SIGN_POINT:
                        operandEntered = false;
                        pointEntered = true;
                        break;
                    default:
                        break;
                }
            } else {
                int stringLen = enteredExpression.length();
                if(lastCharIsAnOperandOrPoint()) {
                    switch (s) {
                        case OPERAND_PLUS:
                        case OPERAND_MINUS:
                        case OPERAND_TIMES:
                        case OPERAND_DIVIDE:
                            if(!enteredExpression.substring(stringLen - 1).equals(SIGN_POINT)) {
                                enteredExpression = enteredExpression.replace(enteredExpression.substring(stringLen - 1), s);
                                mShownText.setText(enteredExpression);
                                operandEntered = true;
                                pointEntered = false;
                            }
                            break;
                        case SIGN_POINT:
                            if(!enteredExpression.substring(stringLen - 1).equals(SIGN_POINT)) {
                                enteredExpression = enteredExpression.replace(enteredExpression.substring(stringLen - 1), s);
                                mShownText.setText(enteredExpression);
                                operandEntered = false;
                                pointEntered = true;
                            }
                            break;

                        default:
                            break;
                    }
                } else {
                    enteredExpression += s;
                }
            }
        }

        mShownText.setText(enteredExpression);
    }

    private boolean lastCharIsAnOperandOrPoint() {
        int stringLen = enteredExpression.length();
        String lastChar = enteredExpression.substring(stringLen-1);
        switch (lastChar) {
            case OPERAND_PLUS:
            case OPERAND_MINUS:
            case OPERAND_TIMES:
            case OPERAND_DIVIDE:
            case SIGN_POINT:
                Log.d(TAG, "lastCharIsAnOperandOrPoint: true returned");
                return true;
            default:
                return false;
        }
    }

    private boolean checkWhetherShownResultExists() {
        if (mShownResult.getText().toString().equals(NO_ENTERED_EXPRESSION)) {
            return false;
        } else {
            return true;
        }
    }

    private double calcResult(String inputString) {
        double result = 0;
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("rhino");
        try {
            result = (double) scriptEngine.eval(inputString);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void showResult(String inputString, boolean reverseResult) {
        double result = calcResult(inputString);
        if(reverseResult) {
            result = result * (-1);
        }
        String stringResult = formatDoubleToString(result);
        mShownResult.setText(stringResult);
        enteredExpression = NO_ENTERED_EXPRESSION;
    }

    private String formatDoubleToString(double result) {
        String stringResult;
        if(Math.floor(result) == Math.ceil(result)) {
            int intResult = (int) result;
            stringResult = String.valueOf(intResult);
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#.#######");
            stringResult = decimalFormat.format(result);
        }
        return stringResult;
    }

    private void calculateResultAndChangeSign() {
        if(enteredExpression.equals(NO_ENTERED_EXPRESSION)) {
            // no enteredExpression means Equals button is called or calculator at initial state
            if(!mShownResult.getText().toString().equals(NO_ENTERED_EXPRESSION)) {
                String stringResult = performChangeSign(mShownResult.getText().toString());
                mShownResult.setText(stringResult);
            }
        } else {
            // there is enteredExpression, then need to calculate result first, then change sign
            showResult(enteredExpression, true);

        }
    }

    private String performChangeSign(String stringResult) {
        double doubleResult = Double.valueOf(stringResult);
        doubleResult = doubleResult * (-1);
        stringResult = formatDoubleToString(doubleResult);

        return stringResult;
    }
}
