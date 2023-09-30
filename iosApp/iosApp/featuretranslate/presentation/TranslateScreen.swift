//
//  TranslateScreen.swift
//  iosApp
//
//  Created by Kecskes David on 13.08.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateScreen: View {
    private var historyDataSource: SqlDelightHistoryDao
    private var translateUseCase: TranslateUseCase
    @ObservedObject var viewModel: IOSTranslateViewModel
    private let voiceToTextParser = IOSVoiceToTextParser()
    
    init(historyDataSource: SqlDelightHistoryDao, translateUseCase: TranslateUseCase) {
        self.historyDataSource = historyDataSource
        self.translateUseCase = translateUseCase
        self.viewModel = IOSTranslateViewModel(historyDataSource: historyDataSource, translateUseCase: translateUseCase)
    }
    
    var body: some View {
        let state = viewModel.state
        
        ZStack {
            List {
                HStack(alignment: .center) {
                    LanguageDropDown(
                        language: state.fromLanguage,
                        isOpen: state.isChoosingFromLanguage,
                        selectLanguage: { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseFromLanguage(language: language))
                        }
                    )
                    Spacer()
                    SwapLanguageButton(
                        onClick: { viewModel.onEvent(event: TranslateEvent.SwapLanguages()) }
                    )
                    Spacer()
                    LanguageDropDown(
                        language: state.toLanguage,
                        isOpen: state.isChoosingToLanguage,
                        selectLanguage: { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseToLanguage(language: language))
                        }
                    )
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                TranslateTextField(
                    fromLanguage: viewModel.state.fromLanguage,
                    fromText: Binding(get: { viewModel.state.fromText }, set: { value in
                        viewModel.onEvent(event: TranslateEvent.ChangeTranslationText(text: value))
                    }),
                    toLanguage: viewModel.state.toLanguage,
                    toText: viewModel.state.toText,
                    isTranslating: viewModel.state.isTranslating,
                    onTranslateEvent: { viewModel.onEvent(event: $0) }
                )
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                if !viewModel.state.history.isEmpty {
                    Text("History")
                        .font(.title)
                        .bold()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.background)
                    
                    HistoryListView(
                        history: viewModel.state.history,
                        onClick: { item in viewModel.onEvent(event: TranslateEvent.SelectHistoryItem(item: item))}
                    )
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.background)
                }
            }
            .listStyle(.plain) // doesn't have that native background
            .buttonStyle(.plain) // each item is not a button
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
    }
}
