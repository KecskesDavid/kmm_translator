//
//  IOSTranslateViewModel.swift
//  iosApp
//
//  Created by Kecskes David on 13.08.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension TranslateScreen {
    @MainActor class IOSTranslateViewModel: ObservableObject {
        private var historyDataSource: SqlDelightHistoryDao
        private var translateUseCase: TranslateUseCase
        
        private let viewModel: TranslateViewModel
        
        @Published var state: TranslateState = TranslateState(
             fromText: "",
             toText: nil,
             isTranslating: false,
             fromLanguage: UiLanguage(imageName: "english", language: .english),
             toLanguage: UiLanguage(imageName: "german", language: .german),
             isChoosingFromLanguage: false,
             isChoosingToLanguage: false,
             error: nil,
             history: []
         )
        private var handle: IDisposableHandle?
        
        init(historyDataSource: SqlDelightHistoryDao, translateUseCase: TranslateUseCase) {
            self.historyDataSource = historyDataSource
            self.translateUseCase = translateUseCase
            self.viewModel = TranslateViewModel(translateUseCase: translateUseCase, historyDao: historyDataSource, coroutineScope: nil)
        }
        
        func startObserving() {
            handle = viewModel.state.subscribe(onCollect: { state in
                if let state = state {
                    self.state = state
                }
            })
        }
        
        func onEvent(event: TranslateEvent) {
            self.viewModel.onEvent(event: event)
        }
        
        func dispose() {
            handle?.dispose()
        }
    }
}
