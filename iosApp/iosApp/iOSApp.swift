import SwiftUI

@main
struct iOSApp: App {
	init() {
		_ = KoinHelper.shared
	}
	
	var body: some Scene {
		WindowGroup {
			CatListView()
		}
	}
}