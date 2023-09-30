//
//  VoiceRecorderButton.swift
//  iosApp
//
//  Created by Kecskes David on 30.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct VoiceRecorderButtonView: View {
    var displayState: DisplayState
    var onClick: () -> Void
    
    var body: some View {
        voiceRecorderButton
    }
}

private extension VoiceRecorderButtonView {
    var voiceRecorderButton: some View {
        ZStack {
            Circle()
                .foregroundColor(.primaryColor)
                .padding()
            
            icon
                .foregroundColor(.onPrimary)
        }
        .frame(maxWidth: 100.0, maxHeight: 100.0)
    }
    
    var icon: some View {
        switch displayState {
        case .speaking:
            return Image(systemName: "stop.fill")
        case .displayResults:
            return Image(systemName: "checkmark")
        default:
            return Image(uiImage: UIImage(named: "mic")!)
        }
    }
}

#Preview {
    VoiceRecorderButtonView(
        displayState: .speaking, onClick: {}
    )
}
