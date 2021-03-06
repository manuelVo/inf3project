package command.server;

import java.util.Iterator;

import server.TcpClient;
import server.Server;
import util.ServerConst;
import util.ServerMessage;

import command.ServerCommand;

public class KickIDCommand extends ServerCommand {

	public KickIDCommand() {
		super(ServerConst.SC_KICK_ID);
	}

	@Override
	protected int routine(Server _src, String _cmd, StringBuilder _mes) {
		int result = 0;
		TcpClient client = null, next;
		try {
			int id = Integer.parseInt(_cmd);
			Iterator<TcpClient> it = _src.getClients().iterator();
			while(it.hasNext() && client == null) {
				next = it.next();
				if(id == next.getPlayer().getWrappedObject().getId()) {
					client = next;
				}
			}
			if(client != null) {
				result = 1;
				_mes.append(String.format("Sucessfully kicked player with ID '%s'\r\n",_cmd));
				client.flushTokenizable(new ServerMessage("you were kicked from the server"));
				client.close();
			} else {
				result = -1;
				_mes.append(String.format("Could not find player with ID '%s'\r\n",_cmd));
			}
		} catch(NumberFormatException nfe) {
			_mes.append(String.format("Invalid ID: '%s'\r\n",_cmd));
			result = -1;
		}
		return result;
	}
}
