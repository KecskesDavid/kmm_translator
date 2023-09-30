//
//  IOSVoiceToTextViewModel.swift
//  iosApp
//
//  Created by Kecskes David on 30.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor class IOSVoiceToTextViewModel: ObservableObject {
    private var voiceToTextParser: IVoiceToTextParser
    private let languageCode: String
    
    private let viewModel: VoiceToTextViewModel
    
    @Published var state = VoiceToTextState(
        powerRatios: [],
        spokenText: "",
        canSpeak: false,
        error: nil,
        displayState: nil
    )
    private var handle: IDisposableHandle?

    init(voiceToTextParser: IVoiceToTextParser, languageCode: String) {
        self.voiceToTextParser = voiceToTextParser
        self.languageCode = languageCode
        self.viewModel = VoiceToTextViewModel(
            parser: voiceToTextParser,
            coroutineScope: nil
        )
    }
    
    func startObserving() {
        handle = viewModel.state.subscribe(onCollect: { [weak self] state in
            if let state = state {
                self?.state = state
            }
        })
    }
    
    func onEvent(event: VoiceToTextEvent) {
        self.viewModel.onEvent(event: event)
    }
    
    func dispose() {
        handle?.dispose()
        onEvent(event: VoiceToTextEvent.Reset())
    }
}
