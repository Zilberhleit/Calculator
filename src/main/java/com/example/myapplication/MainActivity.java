package com.example.myapplication;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
    import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {
    TextView Work;
    TextView Result;
    String working = "";
    String formula = "";
    String tempFormula = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextViews();
    }

    public void initTextViews() {
        Work = findViewById(R.id.textWork);
        Result = findViewById(R.id.textResult);
    }

    public void setWorking(String symbol) {
        if (symbol != "") {
            working += symbol;
        } else {
            working = "";
        }
        Work.setText(working);
    }

    public void clickZero(View view) {
        setWorking("0");
    }

    public void clear(View view) {
        setWorking("");
    }

    public void clickOpen(View view) {
        setWorking("(");
    }

    public void clickClose(View view) {
        setWorking(")");
    }

    public void clickTop(View view) {
        setWorking(".");
    }

    public void clickMinusNumber(View view) {

    }

    public void clickDelete(View view) {
    }

    public void clickEqual(View view) {

        Double result = null;
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        Power();
        //Sinus();
        //Cosines();
        //Derivative();
        Log.d("FORMULA", formula);
        if (working.contains("sin(")) {
            Sinus();
            try {
                result = (double) engine.eval(formula);
            } catch (ScriptException e) {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            }

            if (result != null)
                Result.setText(String.valueOf(result.doubleValue()));
                setWorking("");
        } else if(working.contains("cos(")){
            Cosines();
            try {
                result = (double) engine.eval(formula);
            } catch (ScriptException e) {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            }

            if (result != null)
                Result.setText(String.valueOf(result.doubleValue()));
                setWorking("");
        } else if(working.contains("F'(x,")){
            Derivative();
            try {
                result = (double) engine.eval(formula);
            } catch (ScriptException e) {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            }

            if (result != null)
                Result.setText(String.valueOf(result.doubleValue()));
                setWorking("");
        } else {
            try {
                result = (double) engine.eval(formula);
            } catch (ScriptException e) {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            }

            if (result != null)
                Result.setText(String.valueOf(result.doubleValue()));
            setWorking("");
        }
    }





    public void clickThree(View view) {
        setWorking("3");
    }

    public void clickTwo(View view) {
        setWorking("2");
    }

    public void clickOne(View view) {
        setWorking("1");
    }

    public void clickPow(View view) {
        setWorking("^");
    }
    public void ChangePowFormula(int index){
        String numberLeft = "";
        String numberRight = "";
        for (int i = index + 1; i < working.length(); i++) {
            if(isNumeric(working.charAt(i))){
                numberRight += working.charAt(i);
            }else break;
        }
        for (int i = index - 1; i >= 0; i--) {
            if(isNumeric(working.charAt(i))){
                numberLeft += working.charAt(i);
            }else break;
        }
        String numberOfPower = numberLeft+"^"+numberRight;
        String MathPow = "Math.pow(" + numberLeft+","+numberRight+")";
        tempFormula = tempFormula.replace(numberOfPower,MathPow);
    }
    public boolean isNumeric(char c){
        if ((c <= '9' && c >= '0') || c == '.') {
            return true;
        }
        return false;
    }
    public void Power(){
        ArrayList<Integer> indexOfPower = new ArrayList<>();
        for (int i = 0; i < working.length(); i++) {
            if (working.charAt(i) == '^'){
                indexOfPower.add(i);
            }
        }
        formula = working;
        tempFormula = working;
        for(Integer index: indexOfPower){
            ChangePowFormula(index);
        }
        formula = tempFormula;
        Log.d("F", formula);
    }
    public void clickSinus(View view) {
        setWorking("sin(");
    }

    public void ChangeSin(int indexSin) {
        String numberOfSin = "";
        for (int i = indexSin+4; i < working.length(); i++) {
            if(isNumeric(working.charAt(i))){
                numberOfSin += working.charAt(i);
            }else break;
        }
        String Sinus = "sin(" + numberOfSin + ")";
        Double MathSin = Math.sin(Math.toRadians(Double.parseDouble(numberOfSin)));
        tempFormula = tempFormula.replace(Sinus,MathSin.toString());
        Log.d("t", tempFormula);
    }
    public void Sinus(){
        ArrayList<Integer> indexOfSin = new ArrayList<>();
        for (int i = 0; i < working.length(); i++) {
            int indSin = working.indexOf("sin(", i);
            if (indSin >= 0){
                indexOfSin.add(indSin);
                i = indSin;
            }
        }
        formula = working;
        tempFormula = working;
        for(Integer indexSin: indexOfSin){
            ChangeSin(indexSin);
        }
        formula = tempFormula;
        Log.d("FSIN", formula);
    }
    public void clickCosines(View view) {
        setWorking("cos(");
    }
    public void Cosines() {
        ArrayList<Integer> indexOfCos = new ArrayList<>();
        for (int i = 0; i < working.length(); i++) {
            int indCos = working.indexOf("cos(", i);
            if (indCos >= 0){
                indexOfCos.add(indCos);
                i = indCos;
            }
        }
        formula = working;
        tempFormula = working;
        for (Integer indexCos: indexOfCos) {
            ChangeCos(indexCos);
        }
        formula = tempFormula;
    }
    public void ChangeCos(int indexCos){
        String numberOfCos = "";
        for (int i = indexCos+4; i < working.length(); i++){
            if(isNumeric(working.charAt(i))){
                numberOfCos += working.charAt(i);
            }else break;
        }
        String Cosines = "cos("+numberOfCos+")";
        Double MathCos = Math.cos(Math.toRadians(Double.parseDouble(numberOfCos)));
        tempFormula = tempFormula.replace(Cosines, MathCos.toString());
    }
    public void clickSix(View view) {
        setWorking("6");
    }

    public void clickFive(View view) {
        setWorking("5");
    }

    public void clickFour(View view) {
        setWorking("4");
    }

    public void clickX(View view) {
        setWorking("x");
    }

    public void clickNine(View view) {
        setWorking("9");
    }

    public void clickEight(View view) {
        setWorking("8");
    }

    public void clickSeven(View view) {
        setWorking("7");
    }

    public void clickPlus(View view) {
        setWorking("+");
    }

    public void clickMinus(View view) {
        setWorking("-");
    }

    public void clickMultiplication(View view) {
        setWorking("*");
    }

    public void clickDivision(View view) {
        setWorking("/");
    }

    public void clickDerivative(View view) { setWorking("F'(x,");}
    public void Derivative(){
        ArrayList<Integer> indexOfDer = new ArrayList<>();
        for (int i = 0; i < working.length(); i++){
            int indDer = working.indexOf("F'(x,");
            if (indDer >= 0) {
                indexOfDer.add(indDer);
                i = indDer;
            }
        }
        for (Integer indexDer: indexOfDer) {
            changeDer(indexDer);
        }
        formula = tempFormula;
    }
    public void changeDer(int indexDer){
        String numberOfDer = "";
        for (int i = indexDer + 5; i < working.length(); i++) {
            if (isNumeric(working.charAt(i))){
                numberOfDer += working.charAt(i);
            }else break;
        }
        String Der = "F'(x," + numberOfDer + ")";
        Double resultDer = Double.parseDouble(numberOfDer)-1.0;
        tempFormula = tempFormula.replace(Der, resultDer.toString());
    }
}