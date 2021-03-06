package arena.skirmish;

import server.TcpClient;
import server.Server;
import arena.Arena;

public class SkirmishArena extends Arena<SkirmishOpponent> {
	// sword > alchemy > magic > sword
	private static final int[][] matrix = 
	{
		{ 0, 1,-1},
		{-1, 0, 1},
		{ 1,-1, 0}
	};
	
	public SkirmishArena(Server _server, TcpClient _owner, TcpClient _challenged, int _rounds) {
		super(_server, _owner, _challenged, _rounds);
	}

	@Override
	protected SkirmishOpponent wrap(TcpClient _cl) {
		return new SkirmishOpponent(_cl);
	}

	@Override
	protected boolean prerequisites() {
		// opposing players have to be at the same position
		return player1.getClient().getPlayer().getWrappedObject().getPosition().equals(player2.getClient().getPlayer().getWrappedObject().getPosition());
	}

	@Override
	protected int[][] getMatrix() {
		return matrix;
	}
}
