import SwiftUI
import shared

struct CatListView: View {
    @StateObject private var viewModel = KoinHelper.shared.getCatsViewModel()
    @State private var showingAddSheet = false
    
    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.cats, id: \.id) { cat in
                    CatItemView(cat: cat) {
                        viewModel.deleteCat(id: cat.id)
                    }
                }
            }
            .navigationTitle("Cats")
            .toolbar {
                Button {
                    showingAddSheet = true
                } label: {
                    Image(systemName: "plus")
                }
            }
            .sheet(isPresented: $showingAddSheet) {
                AddCatView(viewModel: viewModel, isPresented: $showingAddSheet)
            }
        }
    }
}

struct CatItemView: View {
    let cat: Cat
    let onDelete: () -> Void
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(cat.name)
                    .font(.headline)
                Text(cat.breed)
                    .font(.subheadline)
                Text("\(cat.age) years old")
                    .font(.caption)
            }
            
            Spacer()
            
            if let imageUrl = cat.imageUrl {
                AsyncImage(url: URL(string: imageUrl)) { image in
                    image
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                } placeholder: {
                    Color.gray
                }
                .frame(width: 60, height: 60)
                .clipShape(RoundedRectangle(cornerRadius: 8))
            }
            
            Button(action: onDelete) {
                Image(systemName: "trash")
                    .foregroundColor(.red)
            }
        }
        .padding(.vertical, 8)
    }
}

struct AddCatView: View {
    let viewModel: CatsViewModelWrapper
    @Binding var isPresented: Bool
    
    @State private var name = ""
    @State private var breed = ""
    @State private var age = ""
    
    var body: some View {
        NavigationView {
            Form {
                TextField("Name", text: $name)
                
                Picker("Breed", selection: $breed) {
                    Text("Select a breed").tag("")
                    ForEach(viewModel.availableBreeds, id: \.self) { breedOption in
                        Text(breedOption).tag(breedOption)
                    }
                }
                
                TextField("Age", text: $age)
                    .keyboardType(.numberPad)
            }
            .navigationTitle("Add New Cat")
            .navigationBarItems(
                leading: Button("Cancel") {
                    isPresented = false
                },
                trailing: Button("Add") {
                    if let ageInt = Int32(age), !name.isEmpty, !breed.isEmpty {
                        viewModel.addCat(
                            name: name,
                            breed: breed,
                            age: ageInt
                        )
                        isPresented = false
                    }
                }
            )
        }
    }
} 