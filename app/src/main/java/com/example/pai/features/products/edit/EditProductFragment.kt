//package com.example.pai.features.products.edit
//
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import android.widget.Spinner
//import android.widget.Toast
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import androidx.navigation.fragment.NavHostFragment.findNavController
//import com.example.pai.R
//import com.example.pai.databinding.EditProductFragmentBinding
//import com.example.pai.utils.hideKeyboard
//import kotlinx.android.synthetic.main.edit_product_fragment.view.*
//import java.util.*
//
///**
// * A simple [Fragment] subclass.
// */
//class EditProductFragment : Fragment(), AdapterView.OnItemSelectedListener {
//
//    private lateinit var binding: EditProductFragmentBinding
//
//    private val viewModel: EditProductViewModel by lazy {
//        val activity = requireNotNull(this.activity) {
//        }
//        ViewModelProviders.of(
//            this,
//            EditProductViewModel.Factory(
//                activity.application,
//                EditProductFragmentArgs.fromBundle(arguments!!).product,
//                EditProductFragmentArgs.fromBundle(arguments!!).isNewProduct
//            )
//        )
//            .get(EditProductViewModel::class.java)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = DataBindingUtil.inflate(
//            inflater,
//            R.layout.edit_product_fragment,
//            container,
//            false
//        )
//        binding.vm = viewModel
//        setObservers()
//        setStatusSpinner()
//        return binding.root
//    }
//
//    private fun setObservers() {
//
//        viewModel.saveProductResponse.observe(viewLifecycleOwner, Observer<Boolean> {
//            if(it) {
//                Toast.makeText(requireContext(), "new item added", Toast.LENGTH_LONG).show()
//                binding.serialNumberInputText.hideKeyboard()
//                val action =
//                    EditProductFragmentDirections.actionEditProductFragmentToProductsFragment()
//                findNavController(this).navigate(action)
//                viewModel.saveProductResponseFinish()
//            }
//        })
//
//        viewModel.updateProductResponse.observe(viewLifecycleOwner, Observer<Boolean> {
//            if(it) {
//                binding.serialNumberInputText.hideKeyboard()
//                Toast.makeText(requireContext(), "item updated", Toast.LENGTH_LONG).show()
//                val action =
//                    EditProductFragmentDirections.actionEditProductFragmentToProductsFragment()
//                findNavController(this).navigate(action)
//                viewModel.updateProductResponseFinish()
//            }
//        })
//        setWarehouseSpinner()
//        setProductTypeSpinner()
//    }
//
//    private fun setWarehouseSpinner() {
//        viewModel.warehousesObservable.observe(viewLifecycleOwner, Observer {
//            createArrayAdapterForSpinner(it, binding.warehouseTypeSpinner)
//            binding.warehouseTypeSpinner.setSelection(viewModel.setCurrentWarehouseType())
//        })
//    }
//
//    private fun setProductTypeSpinner() {
//        viewModel.productTypesObservable.observe(viewLifecycleOwner, Observer {
//            createArrayAdapterForSpinner(it, binding.productTypeSpinner)
//            binding.productTypeSpinner.setSelection(viewModel.setCurrentProductType())
//        })
//    }
//
//    private fun setStatusSpinner() {
//        createArrayAdapterForSpinner(viewModel.status, binding.statusSpinner)
//        binding.statusSpinner.setSelection(viewModel.setCurrentStatus())
//    }
//
//    private fun <T> createArrayAdapterForSpinner(objects: List<T>, spinner: Spinner) {
//        ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_spinner_item,
//            objects
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner.adapter = adapter
//        }
//        spinner.onItemSelectedListener = this
//    }
//
//    override fun onNothingSelected(p0: AdapterView<*>?) {
//    }
//
//    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//        when {
//            (p0 as View).warehouse_type_spinner != null -> {
//                viewModel.productDtoToSave?.warehouseId = p0.getItemAtPosition(p2) as UUID
//            }
//            (p0 as View).product_type_spinner != null -> {
//                viewModel.productDtoToSave?.productTypeId = p0.getItemAtPosition(p2) as UUID
//            }
//            (p0 as View).status_spinner != null -> {
//                viewModel.productDtoToSave?.status = p0.getItemAtPosition(p2) as String
//            }
//        }
//    }
//}
