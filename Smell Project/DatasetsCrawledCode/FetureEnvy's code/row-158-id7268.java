 @Override
 protected void fillMenuBeforeShow(final IMenuManager manager) {
 manager.add(openAction);
 manager.add(downloadToAction);
 manager.add(addAttachmentAction);
 manager.add(deleteAttachmentAction);


 manager.add(new Separator());


 final DownloadAttachmentOpenType preferredOpenType = DownloadAttachmentOpenType.getPreferredOpenType();
 if (DownloadAttachmentOpenType.BROWSER == preferredOpenType) {
 manager.add(openLocallyAction);
        } else {
 manager.add(openInBrowserAction);
        }


 manager.add(copyUrlToClipboardAction);
    }