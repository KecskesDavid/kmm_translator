//
//  VoiceToTextScreen.swift
//  iosApp
//
//  Created by Kecskes David on 30.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct VoiceToTextScreen: View {
    
    private let onResult: (String) -> Void
    
    @ObservedObject var viewModel: IOSVoiceToTextViewModel
    private let voiceToTextParser: IVoiceToTextParser
    private let languageCode: String
    
    @Environment(\.presentationMode) var presentation
    
    init(onResult: @escaping (String) -> Void, voiceToTextParser: IVoiceToTextParser, languageCode: String) {
        self.onResult = onResult
        self.voiceToTextParser = voiceToTextParser
        self.languageCode = languageCode
        self.viewModel = IOSVoiceToTextViewModel(
            voiceToTextParser: voiceToTextParser,
            languageCode: languageCode
        )
    }
    
    
    var body: some View {
        VStack {
            Spacer()
            
            mainView
            
            Spacer()
            
            HStack {
                Spacer()
                VoiceRecorderButtonView(
                    displayState: viewModel.state.displayState ?? .waitToTalk,
                    onClick: {
                        if viewModel.state.displayState != .displayResults {
                            viewModel.onEvent(event: VoiceToTextEvent.ToggleRecording(languageCode: languageCode))
                        } else {
                            onResult(viewModel.state.spokenText)
                            self.presentation.wrappedValue.dismiss()
                        }
                    }
                )
                if viewModel.state.displayState == .displayResults {
                    Button(action: {viewModel.onEvent(event: VoiceToTextEvent.ToggleRecording(languageCode: languageCode))}) {
                        Image(systemName: "arrow.clockwise").foregroundColor(.lightBlue)
                    }
                }
                
                Spacer()
            }
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
        .background(Color.background)
    }
}

private extension VoiceToTextScreen {
    var mainView: some View {
        if let displayState = viewModel.state.displayState {
            switch displayState {
            case .waitToTalk:
                return AnyView(
                    Text("Click record and start talking.")
                        .font(.title2)
                )
            case .displayResults:
                return AnyView(
                    Text(viewModel.state.spokenText)
                        .font(.title2)
                )
            case .error:
                return AnyView(
                    Text(viewModel.state.error ?? "Unknown error")
                        .font(.title2)
                        .foregroundColor(.red)
                )
            case .speaking:
                return AnyView(
                    VoiceRecorderDisplay(
                        powerRatios: viewModel.state.powerRatios.map { Double(truncating: $0) }
                    )
                    .frame(maxHeight: 100)
                    .padding()
                )
            default: return AnyView(EmptyView())
            }
        } else {
            return AnyView(EmptyView())
        }
    }
}
