package com.smartmadsoft.xposed.aio.tweaks;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;

@TargetApi(17)
public class CompactVolumePanel {
    public static final String PKG = "com.android.systemui";

    public static void hook(XC_InitPackageResources.InitPackageResourcesParam iprparam) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                iprparam.res.hookLayout(PKG, "layout", "volume_dialog_row", new XC_LayoutInflated() {
                    @Override
                    public void handleLayoutInflated(XC_LayoutInflated.LayoutInflatedParam liparam) throws Throwable {
                        final int volumeDialogRow = liparam.res.getIdentifier("volume_dialog_row", "id", PKG);
                        RelativeLayout layoutVolumeDialogRow = (RelativeLayout) liparam.view.findViewById(volumeDialogRow);
                        layoutVolumeDialogRow.setPadding(0, 0, 0, 0);

                        final int volumeRowIcon = liparam.res.getIdentifier("volume_row_icon", "id", PKG);
                        final ImageView imageVolumeRowIcon = (ImageView) liparam.view.findViewById(volumeRowIcon);
                        imageVolumeRowIcon.getLayoutParams().height = imageVolumeRowIcon.getLayoutParams().height / 3 * 2;

                        final int volumeSettingsButton = liparam.res.getIdentifier("volume_settings_button", "id", PKG);
                        final ImageView imageVolumeSettingsButton = (ImageView) liparam.view.findViewById(volumeSettingsButton);
                        if (imageVolumeSettingsButton != null)
                            imageVolumeSettingsButton.getLayoutParams().height = imageVolumeSettingsButton.getLayoutParams().height / 3 * 2;

                        // MM TW
                        if (Build.MANUFACTURER.toLowerCase().contains("samsung")) {
                            final int volumeRowHeaderText = liparam.res.getIdentifier("volume_row_header", "id", PKG);
                            TextView textVolumeRowHeader = (TextView) liparam.view.findViewById(volumeRowHeaderText);
                            textVolumeRowHeader.getLayoutParams().height = 0;

                            final int volumeRowSliderBar = liparam.res.getIdentifier("volume_row_slider", "id", PKG);
                            SeekBar barVolumeRowSlider = (SeekBar) liparam.view.findViewById(volumeRowSliderBar);
                            barVolumeRowSlider.setPadding(barVolumeRowSlider.getPaddingLeft() / 2, 0, barVolumeRowSlider.getPaddingLeft() / 2, 0);

                            final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) barVolumeRowSlider.getLayoutParams();
                            params.removeRule(RelativeLayout.END_OF);
                            params.removeRule(RelativeLayout.START_OF);
                            params.addRule(RelativeLayout.END_OF, volumeRowIcon);
                            params.addRule(RelativeLayout.START_OF, volumeSettingsButton);
                            barVolumeRowSlider.setLayoutParams(params);
                        }
                    }
                });
                iprparam.res.hookLayout(PKG, "layout", "volume_dialog", new XC_LayoutInflated() {
                    @Override
                    public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                        final int volumeDialog = liparam.res.getIdentifier("volume_dialog", "id", PKG);
                        final RelativeLayout layoutVolumeDialog = (RelativeLayout) liparam.view.findViewById(volumeDialog);

                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) layoutVolumeDialog.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        layoutVolumeDialog.setLayoutParams(layoutParams);

                        // S7 Nougat
                        layoutVolumeDialog.setPadding(0, 0, 0, 0);

                        final int volumeExpandButton = liparam.res.getIdentifier("volume_expand_button", "id", PKG);
                        View viewVolumeExpandButton = (View) liparam.view.findViewById(volumeExpandButton);
                        viewVolumeExpandButton.getLayoutParams().height = viewVolumeExpandButton.getLayoutParams().height / 3 * 2;

                        final int volumeDialogContent = liparam.res.getIdentifier("volume_dialog_content", "id", PKG);
                        final LinearLayout layoutVolumeDialogContent = (LinearLayout) liparam.view.findViewById(volumeDialogContent);
                        layoutVolumeDialogContent.setPadding(0, 0, 0, 0);

                        // S7 Nougat
                        final int volumeDialogDnd = liparam.res.getIdentifier("volume_dialog_dnd", "id", PKG);
                        final LinearLayout layoutVolumeDialogDnd = (LinearLayout) liparam.view.findViewById(volumeDialogDnd);
                        if (layoutVolumeDialogDnd != null)
                            layoutVolumeDialogDnd.getLayoutParams().height = 0;
                    }
                });
            } else {
                iprparam.res.hookLayout(PKG, "layout", "volume_panel_item", new XC_LayoutInflated() {
                    @Override
                    public void handleLayoutInflated(XC_LayoutInflated.LayoutInflatedParam liparam) throws Throwable {
                        final int streamIcon = liparam.res.getIdentifier("stream_icon", "id", PKG);
                        final ImageView imageStreamIcon = (ImageView) liparam.view.findViewById(streamIcon);
                        imageStreamIcon.getLayoutParams().height = imageStreamIcon.getLayoutParams().height / 3 * 2;

                        final int furtherOptions = liparam.res.getIdentifier("further_options", "id", PKG);
                        final ImageView imageFurtherOptions = (ImageView) liparam.view.findViewById(furtherOptions);
                        imageFurtherOptions.getLayoutParams().height = imageFurtherOptions.getLayoutParams().height / 3 * 2;
                    }
                });

                iprparam.res.hookLayout(PKG, "layout", "volume_panel", new XC_LayoutInflated() {
                    @Override
                    public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                        final int sliderPanel = liparam.res.getIdentifier("slider_panel", "id", PKG);
                        final LinearLayout layoutSliderPanel = (LinearLayout) liparam.view.findViewById(sliderPanel);
                        layoutSliderPanel.setPadding(0, 0, 0, 0);
                    }
                });

                iprparam.res.hookLayout(PKG, "layout", "zen_mode_panel", new XC_LayoutInflated() {
                    @Override
                    public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                        int zenModePanelBgContainer = liparam.res.getIdentifier("zen_mode_panel_bg_container", "id", PKG);
                        FrameLayout layoutZenModePanelBgContainer = (FrameLayout) liparam.view.findViewById(zenModePanelBgContainer);
                        if (layoutZenModePanelBgContainer == null) {
                            zenModePanelBgContainer = liparam.res.getIdentifier("zen_buttons_container", "id", PKG);
                            layoutZenModePanelBgContainer = (FrameLayout) liparam.view.findViewById(zenModePanelBgContainer);
                        }
                        layoutZenModePanelBgContainer.setMinimumHeight(0);

                        final int zenButtons = liparam.res.getIdentifier("zen_buttons", "id", PKG);
                        final LinearLayout layoutZenButtons = (LinearLayout) liparam.view.findViewById(zenButtons);
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) layoutZenButtons.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        layoutZenButtons.setLayoutParams(layoutParams);

                        final int zenSubheadCollapsed = liparam.res.getIdentifier("zen_subhead_collapsed", "id", PKG);
                        final TextView textZenSubheadCollapsed = (TextView) liparam.view.findViewById(zenSubheadCollapsed);

                        final int zenSubhead = liparam.res.getIdentifier("zen_subhead", "id", PKG);
                        final RelativeLayout layoutZenSubhead = (RelativeLayout) liparam.view.findViewById(zenSubhead);
                        layoutZenSubhead.getLayoutParams().height = textZenSubheadCollapsed.getLayoutParams().height;

                        final int zenConditions = liparam.res.getIdentifier("zen_conditions", "id", PKG);
                        final LinearLayout layoutZenConditions = (LinearLayout) liparam.view.findViewById(zenConditions);
                        layoutZenConditions.setPadding(0, 0, 0, 0);
                    }
                });
            }
        } catch (Throwable t) {
            XposedBridge.log(t);
        }
    }
}
