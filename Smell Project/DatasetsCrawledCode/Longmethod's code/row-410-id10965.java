 @Override
 public java.lang.String toString() {
 java.lang.StringBuilder sb = new java.lang.StringBuilder("SupervisorInfo(");
 boolean first = true;


 sb.append("time_secs:");
 sb.append(this.time_secs);
 first = false;
 if (!first) sb.append(", ");
 sb.append("hostname:");
 if (this.hostname == null) {
 sb.append("null");
    } else {
 sb.append(this.hostname);
    }
 first = false;
 if (is_set_assignment_id()) {
 if (!first) sb.append(", ");
 sb.append("assignment_id:");
 if (this.assignment_id == null) {
 sb.append("null");
      } else {
 sb.append(this.assignment_id);
      }
 first = false;
    }
 if (is_set_used_ports()) {
 if (!first) sb.append(", ");
 sb.append("used_ports:");
 if (this.used_ports == null) {
 sb.append("null");
      } else {
 sb.append(this.used_ports);
      }
 first = false;
    }
 if (is_set_meta()) {
 if (!first) sb.append(", ");
 sb.append("meta:");
 if (this.meta == null) {
 sb.append("null");
      } else {
 sb.append(this.meta);
      }
 first = false;
    }
 if (is_set_scheduler_meta()) {
 if (!first) sb.append(", ");
 sb.append("scheduler_meta:");
 if (this.scheduler_meta == null) {
 sb.append("null");
      } else {
 sb.append(this.scheduler_meta);
      }
 first = false;
    }
 if (is_set_uptime_secs()) {
 if (!first) sb.append(", ");
 sb.append("uptime_secs:");
 sb.append(this.uptime_secs);
 first = false;
    }
 if (is_set_version()) {
 if (!first) sb.append(", ");
 sb.append("version:");
 if (this.version == null) {
 sb.append("null");
      } else {
 sb.append(this.version);
      }
 first = false;
    }
 if (is_set_resources_map()) {
 if (!first) sb.append(", ");
 sb.append("resources_map:");
 if (this.resources_map == null) {
 sb.append("null");
      } else {
 sb.append(this.resources_map);
      }
 first = false;
    }
 if (is_set_server_port()) {
 if (!first) sb.append(", ");
 sb.append("server_port:");
 sb.append(this.server_port);
 first = false;
    }
 sb.append(")");
 return sb.toString();
  }