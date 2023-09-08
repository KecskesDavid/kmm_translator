//
//  TranslateTextField.swift
//  iosApp
//
//  Created by Kecskes David on 20.08.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import UniformTypeIdentifiers

struct TranslateTextField: View {
    let fromLanguage: UiLanguage
    @Binding var fromText: String
    let toLanguage: UiLanguage
    let toText: String?
    let isTranslating: Bool
    let onTranslateEvent: (TranslateEvent) -> Void
        
    var body: some View {
        if toText == nil || isTranslating {
            IdleTextField(
                fromText: $fromText,
                isTranslating: isTranslating,
                onTranslateEvent: onTranslateEvent
            )
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
        } else {
            TranslatedTextField(
                fromLanguage: fromLanguage,
                fromText: fromText,
                toLanguage: toLanguage,
                toText: toText ?? "",
                onTranslateEvent: onTranslateEvent
            )
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
            .onTapGesture {
                onTranslateEvent(TranslateEvent.EditTranslation())
            }
        }
    }
}

private extension TranslateTextField {
    struct IdleTextField: View {
        @Binding var fromText: String
        let isTranslating: Bool
        let onTranslateEvent: (TranslateEvent) -> Void
        
        var body: some View {
            TextEditor(text: $fromText)
                .frame(
                    maxWidth: .infinity,
                    minHeight: 200,
                    alignment: .topLeading
                )
                .padding()
                .foregroundColor(Color.onSurface)
                .overlay(alignment: .bottomTrailing) {
                    ProgressButton(
                        text: "Translate",
                        isLoading: isTranslating,
                        onClick: { onTranslateEvent(TranslateEvent.Translate()) }
                    )
                    .padding(.trailing)
                    .padding(.bottom)
                }
                .onAppear {
                    UITextView.appearance().backgroundColor = .clear
                }
            
        }
    }
    
    struct TranslatedTextField: View {
        let fromLanguage: UiLanguage
        let fromText: String
        let toLanguage: UiLanguage
        let toText: String
        let onTranslateEvent: (TranslateEvent) -> Void
        let tts = TextToSpeech()

        var body: some View {
            VStack(alignment: .leading) {
                LanguageDisplayView(language: fromLanguage)
                Text(fromText)
                    .foregroundColor(.onSurface)
                HStack {
                    Spacer()
                    
                    Button(action: { UIPasteboard.general.setValue(
                        fromText,
                        forPasteboardType: UTType.plainText.identifier
                    )}) {
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template)
                            .foregroundColor(.lightBlue)
                    }
                    
                    Button(action: { onTranslateEvent(TranslateEvent.CloseTranslation()) }) {
                        Image(systemName: "xmark")
                            .renderingMode(.template)
                            .foregroundColor(.lightBlue)
                    }
                }
                
                Divider().padding()
                
                LanguageDisplayView(language: toLanguage)
                
                Text(toText)
                    .foregroundColor(.onSurface)
                HStack {
                    Spacer()
                    
                    Button(action: { UIPasteboard.general.setValue(
                        fromText,
                        forPasteboardType: UTType.plainText.identifier
                    )}) {
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template)
                            .foregroundColor(.lightBlue)
                    }
                    
                    Button(action: {
                        tts.speack(text: toText, language: toLanguage.language.langCode)
                    }) {
                        Image(systemName: "speaker.wave.2")
                            .renderingMode(.template)
                            .foregroundColor(.lightBlue)
                    }
                }
            }
        }
    }
}

struct TranslateTextField_Previews: PreviewProvider {
    static var previews: some View {
        TranslateTextField(
            fromLanguage: UiLanguage(imageName: "german", language: .german),
            fromText: Binding(
                get: {"text"},
                set: {value in}
            ),
            toLanguage: UiLanguage(imageName: "english", language: .english),
            toText: nil,
            isTranslating: false,
            onTranslateEvent: { event in }
        )
    }
}


