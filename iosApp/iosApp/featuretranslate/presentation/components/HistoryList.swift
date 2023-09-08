//
//  HistoryList.swift
//  iosApp
//
//  Created by Kecskes David on 08.09.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HistoryListView: View {
    let history: [UiHistoryItem]
    let onClick: (UiHistoryItem) -> Void
    
    var body: some View {
        historyList
    }
}

extension HistoryListView {
    var historyList: some View {
        ForEach(history.reversed(), id: \.id) {
            HistoryItemView(
                uiHistoryItem: $0,
                onClick: { onClick($0) }
            )
        }
    }
}

struct HistoryList_Previews: PreviewProvider {
    static var previews: some View {
        HistoryListView(
            history: [
                UiHistoryItem(
                    id: 123,
                    fromText: "Test text",
                    toText:"Testtext",
                    fromLanguage:UiLanguage(imageName: "german", language:.german),
                    toLanguage: UiLanguage(imageName: "english", language: .english)
                ),
                UiHistoryItem(
                    id: 123,
                    fromText: "Test text",
                    toText:"Testtext",
                    fromLanguage:UiLanguage(imageName: "german", language:.german),
                    toLanguage: UiLanguage(imageName: "english", language: .english)
                ),
                UiHistoryItem(
                    id: 123,
                    fromText: "Test text",
                    toText:"Testtext",
                    fromLanguage:UiLanguage(imageName: "german", language:.german),
                    toLanguage: UiLanguage(imageName: "english", language: .english)
                )
            ],
            onClick: { _ in }
        )
    }
}
