import Foundation
import shared

final class KoinHelper {
    static let shared = KoinHelper()
    private let viewModel: CatsViewModelWrapper
    
    private init() {
        // Initialize Koin
        let module = IosModule()
        module.doInitKoin()
        
        // Get the view model from the provider
        let provider = ViewModelProvider()
        let catsViewModel = provider.getCatsViewModel()
        
        // Create the wrapper
        viewModel = CatsViewModelWrapper(viewModel: catsViewModel)
    }
    
    func getCatsViewModel() -> CatsViewModelWrapper {
        return viewModel
    }
} 