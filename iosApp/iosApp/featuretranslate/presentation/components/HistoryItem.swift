//
//  HistoryItem.swift
//  iosApp
//
//  Created by Kecskes David on 08.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HistoryItemView: View {
    let uiHistoryItem: UiHistoryItem
    let onClick: (UiHistoryItem) -> Void
    
    var body: some View {
        historyItem
    }
}

private extension HistoryItemView {
    var historyItem: some View {
        Button(action: { onClick(uiHistoryItem) }) {
            VStack(spacing: 30) {
                HStack {
                    SmallLanguageIcon(language: uiHistoryItem.fromLanguage)
                    
                    Text(uiHistoryItem.fromText).foregroundColor(.onSurface)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                
                HStack {
                    SmallLanguageIcon(language: uiHistoryItem.toLanguage)
                    
                    Text(uiHistoryItem.toText).foregroundColor(.onSurface)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .frame(maxWidth: .infinity)
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .shadow(radius: 4)
        }
    }
}

struct HistoryItem_Previews: PreviewProvider {
    static var previews: some View {
        HistoryItemView(
            uiHistoryItem: UiHistoryItem(
                id: 123,
                fromText: "Test text",
                toText:"Testtext",
                fromLanguage:UiLanguage(imageName: "german", language:.german),
                toLanguage: UiLanguage(imageName: "english", language: .english)
            ), onClick: {_ in }
        )
    }
}
