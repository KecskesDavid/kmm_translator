//
//  TextToSpeech.swift
//  iosApp
//
//  Created by Kecskes David on 08.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import AVFAudio

struct TextToSpeech {
    private let synthesizer = AVSpeechSynthesizer()
    
    func speack(
        text: String,
        language: String
    ) {
        let utterance = AVSpeechUtterance(string: text)
        utterance.voice = AVSpeechSynthesisVoice(language: language)
        utterance.volume = 1
        synthesizer.speak(utterance)
    }
}
