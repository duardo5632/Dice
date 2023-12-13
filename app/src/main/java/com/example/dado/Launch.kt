package com.example.dado

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.dado.databinding.FragmentLaunchBinding

class Launch : Fragment() {

    private var binding: FragmentLaunchBinding? = null

    //dice numbers
    private var number = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchBinding.inflate(inflater, container, false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countries = resources.getStringArray(R.array.cantidad)
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.list_item,
            countries
        )

        binding!!.amount.setAdapter(adapter)
        binding!!.amount.setOnItemClickListener { _, _, position, _ ->
            number = countries[position].toInt()
            //update
            cantidad()
        }

        //number of dice
        val roolbutton = binding!!.button
        roolbutton.setOnClickListener { cantidad() }

    }


    /**
     * throw dice randomly
     * */
    class Faces(private val numSides: Int) {
        /**
         * Make a random dice roll and return the result.
         */
        fun `throw`(): Int {
            return (1..numSides).random()
        }
    }

    //tells you which side is the random value and sets it
    private fun cantidad() {
        val caras = Faces(6)

        val diceResult = ArrayList<Int>()
        repeat(number) {
            val lanzar = caras.`throw`()
            diceResult.add(lanzar)
        }

        showDiceResult(diceResult)
    }

    private fun showDiceResult(diceResults: List<Int>) {

        //the second die disappears
        if (number == 1) {
            binding?.image2?.visibility = View.GONE
            val animImagen1Reverse = ObjectAnimator.ofFloat(binding?.image, "translationX", 0f)
            val animSetReverse = AnimatorSet()
            animSetReverse.play(animImagen1Reverse)
            animSetReverse.duration = 1000 // Duración de la animación en milisegundos

            Handler().postDelayed({
                animSetReverse.start()
            }, 1000)
        }

        //so that the second die can be seen
        if (number == 2){
            binding?.image2?.visibility = View.VISIBLE
        }

        //given one
        val given = binding!!.image

        val amount = binding?.choose

        if (number == 1) {
            // Display only one die
            amount?.let {
                it.animate().alpha(0f).setDuration(1000).withEndAction {
                    it.visibility = View.GONE
                }.start()
            }
            given.visibility = View.VISIBLE

            val drawableResource = when (diceResults.firstOrNull()) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }
            given.setImageResource(drawableResource)
            given.contentDescription = diceResults.firstOrNull()?.toString() ?: ""

        }  else if (number == 2) {
            //to make the text disappear
            amount?.let {
                it.animate().alpha(0f).setDuration(1000).withEndAction {
                    it.visibility = View.GONE
                }.start()
            }
            given.visibility = View.VISIBLE

            // Display two dice, side by side
            // Updates images and layout accordingly to show two dice
            val drawableResource1 = when (diceResults.getOrNull(0)) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }

            val drawableResource2 = when (diceResults.getOrNull(1)) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }

            binding?.apply {
                //Update images in images and images1
                image.setImageResource(drawableResource1)
                image2.setImageResource(drawableResource2)

                // Transition animation between images
                val animImagen1 = ObjectAnimator.ofFloat(image, "translationX", -280f)
                val animImagen2 = ObjectAnimator.ofFloat(image2, "alpha", 1f)


                val animSet = AnimatorSet()
                animSet.playTogether(animImagen1)
                animSet.playTogether(animImagen2)
                animSet.duration = 1000 // Animation duration in milliseconds

                animSet.start()
            }
        }
    }
}