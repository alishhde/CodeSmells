public class TransactionIdConversion {


 static KahaTransactionInfo convertToLocal(TransactionId tx) {
 KahaTransactionInfo rc = new KahaTransactionInfo();
 LocalTransactionId t = (LocalTransactionId) tx;
 KahaLocalTransactionId kahaTxId = new KahaLocalTransactionId();
 kahaTxId.setConnectionId(t.getConnectionId().getValue());
 kahaTxId.setTransactionId(t.getValue());
 rc.setLocalTransactionId(kahaTxId);
 return rc;
    }


 static KahaTransactionInfo convert(TransactionId txid) {
 if (txid == null) {
 return null;
        }
 KahaTransactionInfo rc;


 if (txid.isLocalTransaction()) {
 rc = convertToLocal(txid);
        } else {
 rc = new KahaTransactionInfo();
 XATransactionId t = (XATransactionId) txid;
 KahaXATransactionId kahaTxId = new KahaXATransactionId();
 kahaTxId.setBranchQualifier(new Buffer(t.getBranchQualifier()));
 kahaTxId.setGlobalTransactionId(new Buffer(t.getGlobalTransactionId()));
 kahaTxId.setFormatId(t.getFormatId());
 rc.setXaTransactionId(kahaTxId);
        }
 return rc;
    }


 static TransactionId convert(KahaTransactionInfo transactionInfo) {
 if (transactionInfo.hasLocalTransactionId()) {
 KahaLocalTransactionId tx = transactionInfo.getLocalTransactionId();
 LocalTransactionId rc = new LocalTransactionId();
 rc.setConnectionId(new ConnectionId(tx.getConnectionId()));
 rc.setValue(tx.getTransactionId());
 return rc;
        } else {
 KahaXATransactionId tx = transactionInfo.getXaTransactionId();
 XATransactionId rc = new XATransactionId();
 rc.setBranchQualifier(tx.getBranchQualifier().toByteArray());
 rc.setGlobalTransactionId(tx.getGlobalTransactionId().toByteArray());
 rc.setFormatId(tx.getFormatId());
 return rc;
        }
    }
}