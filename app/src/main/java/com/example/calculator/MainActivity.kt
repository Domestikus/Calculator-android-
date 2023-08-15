package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private var strNumber = StringBuilder()

    //string is immutable, (bts creating several string objects in the memory so stringbuilder is used. its mutable)

    private lateinit var binding: ActivityMainBinding
    //lateinit = late initialization

    private lateinit var numberButton: Array<Button>
    private lateinit var functionButton: List<Button>
    private var operator: Operator = Operator.NONE
    private var isOperatorClicked: Boolean = false
    private var operand1: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializedComponents()

    }

    private fun initializedComponents() {
        strNumber.append("0.0")

        numberButton = arrayOf(
            binding.button0,
            binding.button00,
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button8,
            binding.button9,
            binding.buttonC,
            binding.buttondel,
            binding.buttonDec
        )

        functionButton = listOf(
            binding.buttonAdd,
            binding.buttonMin,
            binding.buttonMult,
            binding.buttonDiv,
            binding.buttonPer
        )

        for (i in numberButton) {
            i.setOnClickListener() {
                numberButtonClicked(i)
            }
        }

        for (i in functionButton) {
            i.setOnClickListener() {
                operatorButtonclick(i)
            }
        }

        binding.buttonEqual.setOnClickListener() { buttonEqualClick() }
    }

    private fun buttonEqualClick() {
        var output: Double = 0.0
        if (!strNumber.isEmpty()) {
            val operand2 = strNumber.toString().toDouble()
            output = when (operator) {
                Operator.ADD -> operand1 + operand2
                Operator.SUB -> operand1 - operand2
                Operator.MUL -> operand1 * operand2
                Operator.DIV -> operand1 / operand2
                Operator.PER -> (operand1 / 100) * operand2
                else -> 0.0
            }
        }
        binding.carry.text = null
        strNumber.clear()
        strNumber.append(output.toString())
        updateDisplay()
        isOperatorClicked = true
    }

    private fun updateDisplay() {
        try {
            val textValue = strNumber.toString()
                .toDouble() //changes the value of strnumber to double so if str is "0000..." its converted to 0
            binding.Result.text = textValue.toString()
        } catch (e: IllegalArgumentException) {
            strNumber.clear()
            binding.Result.text = "ERROR"
        }
    }

    private fun operatorButtonclick(btn: Button) {
        if (btn.text == "+" && strNumber.toString().toDouble() == 0.0)
        else if (btn.text == "-" && strNumber.toString().toDouble() == 0.0  && strNumber.toString()!="-0.0") strNumber.insert(0, "-")
        else if (btn.text == "+" && strNumber.toString().toDouble() != 0.0) operator = Operator.ADD
        else if (btn.text == "-" && strNumber.toString().toDouble() != 0.0) operator = Operator.SUB
        else if (btn.text == "x" && strNumber.toString().toDouble() != 0.0) operator = Operator.MUL
        else if (btn.text == "/" && strNumber.toString().toDouble() != 0.0) operator = Operator.DIV
        else if (btn.text == "%" && strNumber.toString().toDouble() != 0.0) operator = Operator.PER
        else operator = Operator.NONE

        if (strNumber.toString() != "-0.0") isOperatorClicked = true

        //Operand is assigned
        operand1 = strNumber.toString().toDouble()

        if (strNumber.toString().toDouble() == 0.0) {
                binding.Result.text = strNumber.toString()
            }
        else {
                binding.carry.text = operand1.toString() + btn.text
                binding.Result.text = "0.0"
            }
    }

    private fun numberButtonClicked(btn: Button) {
            if (strNumber.toString() == "0.0") strNumber.clear()

            if (isOperatorClicked) {
                operand1.toString().toDouble()
                strNumber.clear()
                isOperatorClicked = false
            } //when any operator is clicked the strNumber or the result is reset

            if (btn.text == "DEL") {
                if (strNumber.length > 1) strNumber.deleteCharAt(strNumber.length - 1)
                else {
                    strNumber.clear()
                    strNumber.append("0")
                }
            } else if (btn.text == "C") {
                strNumber.clear()
                strNumber.append("0.0")
                binding.carry.text = null
            } else {
                if (strNumber.toString()=="-0.0" || strNumber.toString()=="-0"){
                    strNumber.clear()
                    strNumber.append("-")
                    strNumber.append(btn.text)
                }
                else strNumber.append(btn.text)
            }

            updateDisplay()
        }


    }



enum class  Operator {ADD, SUB, MUL, DIV, PER, NONE}