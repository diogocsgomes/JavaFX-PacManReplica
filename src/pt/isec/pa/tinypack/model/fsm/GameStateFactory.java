package pt.isec.pa.tinypack.model.fsm;

import pt.isec.pa.tinypack.model.fsm.states.*;

public class GameStateFactory {



    public IGameState changeState(GameState state, GameData GameEntities, Context context,GhostMovement ghostMovement)
    {

       return switch (state)
               {
                   case PRE_GAME -> new PreGameState(GameEntities,context,ghostMovement);

                   case FANTOMS_DONT_MOVE -> new FantomsDontMoveState(GameEntities,context,ghostMovement);

                   case ONGOING_GAME -> new OngoingGameState(GameEntities,context,ghostMovement);

                   case ONGOING_GAME_POWER -> new OngoingGameState_PowerState(GameEntities,context,ghostMovement);

                  // case LEVEL_TRANSITION -> new LevelTransitionState(GameEntities,context);

                   //case GAME_PAUSED -> new GamePausedState(GameEntities,context,context.state);
                   case GAME_PAUSED -> new GamePausedState(GameEntities,context,ghostMovement);

                   //case GAME_OVER -> new GameOverState(GameEntities,context);

                   //case PAC_MAN_DEAD -> new PacManDeadState(GameEntities,context);


                   default -> null;
               };



    }




}
