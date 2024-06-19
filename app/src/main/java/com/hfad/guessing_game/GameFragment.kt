package com.hfad.guessing_game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hfad.guessing_game.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false).apply {
            composeView.setContent {
                MaterialTheme {
                    Surface {
                        GameFragmentContent(viewModel)
                    }
                }
            }
        }

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
       binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

         viewModel.gameOver.observe(viewLifecycleOwner) { newValue ->
             if (newValue) {
                 val action = GameFragmentDirections
                     .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                 view.findNavController().navigate(action)
             }
         }

        binding.guessButton.setOnClickListener {
           viewModel. makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null

        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    @Composable
    fun FinishGameButton(clicked: ()-> Unit) {
        Button(onClick = clicked) {
            Text("Finish Game")

        }
    }
    @Composable
    fun EnterGuess(guess:String,changed: (String) -> Unit) {
        TextField(value = guess,
            label = { Text("Guess a letter") },
            onValueChange =changed
        )
    }
    @Composable
    fun GuessButton(clicked: () -> Unit) {
        Button(onClick = clicked) {
            Text("Guess!")

        }
    }
    @Composable
    fun GameFragmentContent(viewModel: GameViewModel) {
        val guess = remeber { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxWidth()) {
            EnterGuess(guess.value) { guess.value = it}

            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                GuessButton {
                    viewModel.makeGuess(guess.value.uppercase())
                    guess.value = ""
                }
                FinishGameButton {
                    viewModel.finishGame()
                }
            }
        }
    }




