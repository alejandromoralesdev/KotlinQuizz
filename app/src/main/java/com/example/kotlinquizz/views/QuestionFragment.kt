package com.example.kotlinquizz.views

import android.os.*
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.kotlinquizz.DataHolder
import com.example.kotlinquizz.R
import com.example.kotlinquizz.databinding.FragmentQuestionBinding
import com.example.kotlinquizz.models.Answer
import com.example.kotlinquizz.models.Question
import kotlinx.android.synthetic.main.fragment_question.*

class QuestionFragment : Fragment() {

    private var _binding: FragmentQuestionBinding? = null
    private val b get() = _binding!!
    lateinit var timer: CountDownTimer
    val allQuestions: ArrayList<Question> = arrayListOf()
    val allButtons: ArrayList<Button> = arrayListOf()
    var pos = 0

    var currentPoints = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        val view = b.root
        return view
    }

    fun initData() {
        DataHolder.points = 0
        allQuestions.addAll(DataHolder.questions.questions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentPoints = b.progressBar.max * 1000

        initData()

        nextQuestion()

        b.btOption1.setOnClickListener {
            checkAnswer(b.btOption1)
        }

        b.btOption2.setOnClickListener {
            checkAnswer(b.btOption2)
        }

        b.btOption3.setOnClickListener {
            checkAnswer(b.btOption3)
        }

        b.btOption4.setOnClickListener {
            checkAnswer(b.btOption4)
        }

        //onClick()
    }

    fun onClick(button: Button) {
        button.setOnClickListener {
            checkAnswer(button)
        }
    }

    fun nextQuestion() {
        if (pos < allQuestions.size) {
            val curQuestion = allQuestions[pos]

            b.tvQuestion.text = curQuestion.title

            b.btOption1.text = curQuestion.answers[0].title
            b.btOption1.tag = 0

            b.btOption2.text = curQuestion.answers[1].title
            b.btOption2.tag = 1

            b.btOption3.text = curQuestion.answers[2].title
            b.btOption3.tag = 2

            b.btOption4.text = curQuestion.answers[3].title
            b.btOption4.tag = 3

            allButtons.clear()
            allButtons.add(b.btOption1)
            allButtons.add(b.btOption2)
            allButtons.add(b.btOption3)
            allButtons.add(b.btOption4)

            timer = countDown()
            timer.start()
        } else {
            activity?.let {
                it.supportFragmentManager.beginTransaction().replace(R.id.framelayout, ReviewFragment())
                    .commit()
            }
            //Toast.makeText(requireContext(), "Has terminado la partida", Toast.LENGTH_LONG).show()
        }


    }

    fun checkAnswer(button: Button) {
        disableButtons(button)
    }

    fun disableButtons(button: Button) {
        val currQuestion = allQuestions[pos]
        val isCorrect = currQuestion.answers[button.tag as Int].isCorrect

        if (isCorrect) {
            button.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.green))
            DataHolder.points += currentPoints
            allButtons.forEach {
                if (button != it) {
                    it.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.grey))
                }
            }
        } else {
            for (i in 0 until allButtons.size) {
                if (currQuestion.answers[i].isCorrect) {
                    allButtons[i].setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.green
                        )
                    )
                } else {
                    allButtons[i].setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.grey
                        )
                    )
                }
            }
            button.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.red))
        }

        blockButtons()
        countDownWait()
    }

    fun blockButtons() {
        allButtons.forEach {
            it.isClickable = false
        }
    }

    fun restoreButtons() {
        allButtons.forEach {
            it.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.purple_500))
            it.isClickable = true
        }
    }

    fun countDown(): CountDownTimer {
        b.progressBar.progress = b.progressBar.max

        val timer = object : CountDownTimer(b.progressBar.max.toLong() * 1000, 1000) {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onTick(millisUntilFinished: Long) {
                currentPoints = millisUntilFinished.toInt()
                b.progressBar.setProgress(b.progressBar.progress - 1, true)
            }

            override fun onFinish() {
                pos++
                nextQuestion()
                restoreButtons()
            }
        }

        return timer
    }

    fun countDownWait() {
        timer.cancel()
        //b.progressBar.progress = b.progressBar.max

        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                pos++
                nextQuestion()
                restoreButtons()
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
