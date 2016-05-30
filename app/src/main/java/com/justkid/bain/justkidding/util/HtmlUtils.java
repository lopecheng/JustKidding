package com.justkid.bain.justkidding.util;

import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.widget.TextView;

import in.uncod.android.bypass.Bypass;
import in.uncod.android.bypass.style.TouchableUrlSpan;

public class HtmlUtils {

    private HtmlUtils() { }

    /**
     * Work around some 'features' of TextView and URLSpans. i.e. vanilla URLSpans do not react to
     * touch so we replace them with our own {@link TouchableUrlSpan}
     * & {@link LinkTouchMovementMethod} to fix this.
     * <p/>
     * Setting a custom MovementMethod on a TextView also alters touch handling (see
     * TextView#fixFocusableAndClickableSettings) so we need to correct this.
     */
    public static void setTextWithNiceLinks(TextView textView, CharSequence input) {
        textView.setText(input);
        textView.setMovementMethod(LinkTouchMovementMethod.getInstance());
        textView.setFocusable(false);
        textView.setClickable(false);
        textView.setLongClickable(false);
    }

    /**
     * Parse the given input using {@link TouchableUrlSpan}s rather than vanilla {@link URLSpan}s
     * so that they respond to touch.
     */
    public static SpannableStringBuilder parseHtml(String input,
                                                   ColorStateList linkTextColor,
                                                   @ColorInt int linkHighlightColor) {
        SpannableStringBuilder spanned = (SpannableStringBuilder) Html.fromHtml(input);

        // strip any trailing newlines
        while (spanned.charAt(spanned.length() - 1) == '\n') {
            spanned = spanned.delete(spanned.length() - 1, spanned.length());
        }

        URLSpan[] urlSpans = spanned.getSpans(0, spanned.length(), URLSpan.class);
        for (URLSpan urlSpan : urlSpans) {
            int start = spanned.getSpanStart(urlSpan);
            int end = spanned.getSpanEnd(urlSpan);
            spanned.removeSpan(urlSpan);
            spanned.setSpan(new TouchableUrlSpan(urlSpan.getURL(), linkTextColor,
                    linkHighlightColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanned;
    }

    public static void parseAndSetText(TextView textView, String input) {
        if (TextUtils.isEmpty(input)) return;
        setTextWithNiceLinks(textView, parseHtml(input, textView.getLinkTextColors(),
                textView.getHighlightColor()));
    }

    /**
     * Parse Markdown and plain-text links.
     * <p/>
     * {@link Bypass} does not handle plain text links (i.e. not md syntax) and requires a
     * {@code String} input (i.e. squashes any spans). {@link Linkify} handles plain links but also
     * removes any existing spans. So we can't just run our input through both.
     * <p/>
     * Instead we use the markdown lib, then take a copy of the output and Linkify
     * <strong>that</strong>. We then find any {@link URLSpan}s and add them to the markdown output.
     * Best of both worlds.
     */
    public static CharSequence parseMarkdownAndPlainLinks(
            TextView textView,
            String input,
            Bypass markdown,
            Bypass.LoadImageCallback loadImageCallback) {
        CharSequence markedUp = markdown.markdownToSpannable(input, textView, loadImageCallback);
        final SpannableString plainLinks = new SpannableString(markedUp); // copy of the md output
        Linkify.addLinks(plainLinks, Linkify.WEB_URLS);
        final URLSpan[] urlSpans = plainLinks.getSpans(0, plainLinks.length(), URLSpan.class);
        if (urlSpans != null && urlSpans.length > 0) {
            // add any plain links to the markdown output
            final SpannableStringBuilder ssb = new SpannableStringBuilder(markedUp);
            for (URLSpan urlSpan : urlSpans) {
                ssb.setSpan(new TouchableUrlSpan(urlSpan.getURL(),
                                textView.getLinkTextColors(),
                                textView.getHighlightColor()),
                        plainLinks.getSpanStart(urlSpan),
                        plainLinks.getSpanEnd(urlSpan),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            markedUp = ssb;
        }
        return markedUp;
    }

    /**
     * Parse Markdown and plain-text links and set on the {@link TextView} with proper clickable
     * spans.
     */
    public static void parseMarkdownAndSetText(TextView textView,
                                               String input,
                                               Bypass markdown,
                                               Bypass.LoadImageCallback loadImageCallback) {
        if (TextUtils.isEmpty(input)) return;
        setTextWithNiceLinks(textView,
                parseMarkdownAndPlainLinks(textView, input, markdown, loadImageCallback));
    }

}
