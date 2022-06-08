package ir.shahabazimi.mydraft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ir.shahabazimi.mydraft.databinding.FragmentFirstBinding
import ir.shahabazimi.mydraft.security.FingerprintPrompt
import ir.shahabazimi.mydraft.security.General
import ir.shahabazimi.mydraft.security.MySp

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding) {
        decryptButton.setOnClickListener {
            if (General.canAuthenticate(requireContext()))
                FingerprintPrompt.show(this@FirstFragment).observe(viewLifecycleOwner) {
                    if (it == false) {
                        decryptedTextView.text = ""
                        Toast.makeText(context, "FINGERPRINT ERROR", Toast.LENGTH_SHORT).show()
                    } else
                        decryptedTextView.text =
                            MySp(requireContext()).load()?.ifEmpty { "NOTHING IS SAVED" }
                }
            else
                Toast.makeText(context, "DEVICE IS NOT SECURED", Toast.LENGTH_SHORT).show()

        }
        encryptButton.setOnClickListener {
            val data = binding.inputEdittext.text.toString()
            if (data.isEmpty())
                Toast.makeText(context, "DATA IS EMPTY", Toast.LENGTH_SHORT).show()
            else {
                if (General.canAuthenticate(requireContext())) {
                    MySp(requireContext()).save(binding.inputEdittext.text.toString())
                    Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "DEVICE IS NOT SECURED", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}