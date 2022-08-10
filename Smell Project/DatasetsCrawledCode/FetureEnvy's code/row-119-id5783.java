 private Action createAction(final ProjectInfo project, final TeamConfiguration team) {
 Check.notNull(project, "project"); //$NON-NLS-1$
 Check.notNull(team, "team"); //$NON-NLS-1$


 final String projectGUID = project.getGUID();


 // Omit the team name for the default team
 final String actionName = team.isDefaultTeam() ? project.getName()
                : MessageFormat.format(
 Messages.getString("TeamExplorerControl.ProjectSlashTeamFormat"), //$NON-NLS-1$
 project.getName(),
 team.getTeamName());


 final Action action = new Action(actionName) {
 @Override
 public void run() {
 final String beforeChangeProjectGUID = context.getCurrentProjectInfo().getGUID();
 if (!projectGUID.equals(beforeChangeProjectGUID) || !team.equals(context.getCurrentTeam())) {
 context.setCurrentProject(projectGUID);
 context.setCurrentTeam(team);


 TFSCommonUIClientPlugin.getDefault().projectOrTeamChanged();


 // Only invoke this listener if team project changed
 if (!projectGUID.equals(beforeChangeProjectGUID)) {
 final boolean tfvc =
 context.getCurrentProjectInfo().getSourceControlCapabilityFlags().contains(
 SourceControlCapabilityFlags.TFS);
 TFSCommonUIClientPlugin.getDefault().sourceControlChanged(tfvc);
                        }
                    }
                }
            };


 if (projectGUID.equals(context.getCurrentProjectInfo().getGUID())
                && team.equals(context.getCurrentTeam())) {
 action.setChecked(true);
            }


 return action;
        }