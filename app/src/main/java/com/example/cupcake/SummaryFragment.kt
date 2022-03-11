package com.example.cupcake

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentSummaryBinding
import com.example.cupcake.model.OrderViewModel

/**
 * [SummaryFragment] contains a summary of the order details with a button to share the order
 * via another app.
 */
class SummaryFragment : Fragment() {

    private val sharedViewModel: OrderViewModel by activityViewModels()

    // Binding object instance corresponding to the fragment_summary.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentSummaryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentSummaryBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            summaryFragment = this@SummaryFragment
        }
    }

    /**
     * Submit the order by sharing out the order details to another app via an implicit intent.
     */
    fun sendOrder() {

        //Toast.makeText(activity, "Send Order", Toast.LENGTH_SHORT).show()
        //В нашем приложении всегда минимум один кекс,
        // но в реальном может быть установлено с нуля
        val numberOfCupcakes = sharedViewModel.quantity.value ?: 0

        val orderSummary = getString(

            R.string.order_details,
//Note: When calling getQuantityString(),
// you need to pass in the quantity twice
// because the first quantity parameter is used
// to select the correct plural string.
// The second quantity parameter is used
// in the %d placeholder of the actual string resource.
            resources.getQuantityString(R.plurals.cupcakes,
                numberOfCupcakes, numberOfCupcakes),
            //sharedViewModel.quantity.value.toString(),
            sharedViewModel.flavor.value.toString(),
            sharedViewModel.date.value.toString(),
            sharedViewModel.price.value.toString()
        )
        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_cupcake_order))
            .putExtra(Intent.EXTRA_TEXT, orderSummary)
            .putExtra(Intent.EXTRA_EMAIL, "blskdf@gmail.com")

        //However, before launching an activity with this intent,
        // check to see if there's an app that could even handle it.
        // This check will prevent the Cupcake app from crashing
        // if there's no app to handle the intent, making your code safer.
        if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        }

    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_summaryFragment_to_startFragment)
    }
}