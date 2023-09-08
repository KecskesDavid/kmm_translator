//
//  LanguageDisplay.swift
//  iosApp
//
//  Created by Kecskes David on 20.08.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDisplayView: View {
    var language: UiLanguage
    
    var body: some View {
        languageDisplay
    }
}

private extension LanguageDisplayView {
    var languageDisplay: some View {
        HStack {
            SmallLanguageIcon(language: language)
                .padding(.trailing, 5)
            
            Text(language.language.langName)
                .foregroundColor(.lightBlue)
        }
    }
}

struct LanguageDisplayView_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDisplayView(language: UiLanguage(imageName: "german", language: .german))
    }
}
