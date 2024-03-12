package com.hfad.guessing_game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hfad.guessing_game.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer { newValue ->
            binding.incorrectGuesses.text = "Incorrect guesses: $newValue"
        })

        viewModel.livesLeft.observe(viewLifecycleOwner, Observer { newValue ->
            binding.lives.text = "you have $newValue lives left"
        })

        viewModel.secretWordDisplay.observe(viewLifecycleOwner, Observer { newValue ->
            binding.word.text = newValue
        })
         viewModel.gameOver.observe(viewLifecycleOwner, Observer { newValue ->
             if (newValue){
                 val action = GameFragmentDirections
                     .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                 view.findNavController().navigate(action)
             }
         })

        binding.guessButton.setOnClickListener() {
           viewModel. makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null

        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}
