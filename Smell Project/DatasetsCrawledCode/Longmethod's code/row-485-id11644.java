 private void setModalFieldsTooltips() {
 // set Tooltips
 this.tooltipInput.setTitle(MSGS.firewallPortForwardFormInboundInterfaceToolTip());
 this.tooltipOutput.setTitle(MSGS.firewallPortForwardFormOutboundInterfaceToolTip());
 this.tooltipLan.setTitle(MSGS.firewallPortForwardFormLanAddressToolTip());
 this.tooltipProtocol.setTitle(MSGS.firewallPortForwardFormProtocolToolTip());
 this.tooltipInternal.setTitle(MSGS.firewallPortForwardFormInternalPortToolTip());
 this.tooltipExternal.setTitle(MSGS.firewallPortForwardFormExternalPortToolTip());
 this.tooltipEnable.setTitle(MSGS.firewallPortForwardFormMasqueradingToolTip());
 this.tooltipPermittedNw.setTitle(MSGS.firewallPortForwardFormPermittedNetworkToolTip());
 this.tooltipPermittedMac.setTitle(MSGS.firewallPortForwardFormPermittedMacAddressToolTip());
 this.tooltipSource.setTitle(MSGS.firewallPortForwardFormSourcePortRangeToolTip());
 this.tooltipInput.reconfigure();
 this.tooltipOutput.reconfigure();
 this.tooltipLan.reconfigure();
 this.tooltipProtocol.reconfigure();
 this.tooltipExternal.reconfigure();
 this.tooltipInternal.reconfigure();
 this.tooltipEnable.reconfigure();
 this.tooltipPermittedNw.reconfigure();
 this.tooltipPermittedMac.reconfigure();
 this.tooltipSource.reconfigure();
    }