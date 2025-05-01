import shared
import Combine

@MainActor
class CatsViewModelWrapper: ObservableObject {
    @Published private(set) var cats: [Cat] = []
    private let viewModel: CatsViewModel
    
    init(viewModel: CatsViewModel) {
        self.viewModel = viewModel
        observeCats()
    }
    
    private func observeCats() {
        viewModel.cats.watch { [weak self] catsList in
            if let cats = catsList as? [Cat] {
                self?.cats = cats
            }
        }
    }
    
    func addCat(name: String, breed: String, age: Int32) {
        viewModel.addCat(name: name, breed: breed, age: age, imageUrl: nil, description: nil)
    }
    
    func deleteCat(id: String) {
        viewModel.deleteCat(id: id)
    }
} 