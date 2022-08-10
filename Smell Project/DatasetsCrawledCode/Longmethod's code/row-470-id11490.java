 @Override
 public boolean equals(Object obj) {
 if (this == obj) {
 return true;
        }
 if (!super.equals(obj)) {
 return false;
        }
 if (getClass() != obj.getClass()) {
 return false;
        }
 ContextResourceLink other = (ContextResourceLink) obj;
 if (factory == null) {
 if (other.factory != null) {
 return false;
            }
        } else if (!factory.equals(other.factory)) {
 return false;
        }
 if (global == null) {
 if (other.global != null) {
 return false;
            }
        } else if (!global.equals(other.global)) {
 return false;
        }
 return true;
    }