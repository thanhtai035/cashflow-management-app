package com.tyme.feature_chatbot.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.tyme.base_feature.common.Result
import com.tyme.feature_chatbot.presentation.adapter.ChatMessage
import com.tyme.feature_chatbot.databinding.ActivityChatBotBinding
import com.tyme.feature_chatbot.presentation.adapter.ChatAdapter
import com.tyme.feature_chatbot.presentation.viewmodel.ChatBotViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatBotFragment : SuperBottomSheetFragment() {
    private lateinit var binding: ActivityChatBotBinding
    private val viewModel: ChatBotViewModel by viewModel()
    private val messages = ArrayList<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater) //initializing the binding class

        binding = ActivityChatBotBinding.inflate(layoutInflater)

        chatAdapter = ChatAdapter(requireContext(), messages)
        binding.chatListView.adapter = chatAdapter

        // Observe the response from chatbot API
        viewModel.receivedMessages.observe(this) { response ->
            when (response) {
                is Result.Success -> {

                    messages[messages.size - 1] = ChatMessage(response.data ?: "", false)
                    chatAdapter.notifyDataSetChanged()
                    binding.chatListView.smoothScrollToPosition(chatAdapter.count)
                    binding.messageInputLayout.visibility = View.VISIBLE

                }
                is Result.Loading -> {
                    binding.messageInputLayout.visibility = View.INVISIBLE

                }
                else -> {
                    messages[messages.size - 1] =
                        ChatMessage("Timeout. Please send another request", false)
                    chatAdapter.notifyDataSetChanged()
                    binding.chatListView.smoothScrollToPosition(chatAdapter.count)
                    binding.messageInputLayout.visibility = View.VISIBLE

                }
            }

        }

        // Edit text listener
        binding.messageEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.isNotEmpty() && s[s.length - 1] == '\n') {
                    sendRequest(s.toString().trim())
                    binding.messageEditText.text.clear()
                }
            }
        })


        // Microphone button listener for speech recognizer
        binding.microphoneButton.setOnClickListener {

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE)
        }
        return binding.root
    }


    // SuperBottomSheet customization
    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }


    override fun getCornerRadius() = 20f * requireContext().resources.displayMetrics.density

    override fun isSheetCancelableOnTouchOutside(): Boolean {
       return true;
    }


    override fun getExpandedHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        return (screenHeight * 0.9).toInt()  // Return 80% of the screen height
    }


    // Callback for speech recognizer
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!matches.isNullOrEmpty()) {
                val spokenText = matches[0]
                sendRequest(spokenText)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val VOICE_RECOGNITION_REQUEST_CODE = 1
    }

    // Send message request and clear edit text
    private fun sendRequest(prompt: String) {
        messages.add(ChatMessage(prompt,true))
        chatAdapter.notifyDataSetChanged()
        binding.messageEditText.text.clear()

        // Message placeholder and loading animation
        messages.add(ChatMessage("", false, true))
        chatAdapter.notifyDataSetChanged()
        binding.chatListView.smoothScrollToPosition(chatAdapter.count)
        viewModel.getMessage(prompt)
    }
}