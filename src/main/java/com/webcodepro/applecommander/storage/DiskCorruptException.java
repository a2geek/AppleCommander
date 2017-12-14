/*
 * AppleCommander - An Apple ][ image utility.
 * Copyright (C) 2002 by Robert Greene
 * robgreene at users.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.webcodepro.applecommander.storage;

/**
 * A DiskCorruptException is thrown during the disk's data structures are corrupted
 * beyound hope of automatic recovering.
 * <br>
 * Created on Nov 30, 2017.
 * @author Lisias Toledo
 */
public class DiskCorruptException extends DiskException {

	private static final long serialVersionUID = 0xFFFFFFFF80000000L;

	public enum Kind {
		RECURSIVE_DIRECTORY_STRUCTURE
	}
	
	final public Kind kind;
	final public Object offender;
	
	private DiskCorruptException(String description) {
		super(description);
		this.kind = null;
		this.offender = null;
	}
	
	/**
	 * Constructor for DiskFullException.
	 */
	public DiskCorruptException(String description, Kind kind, Object offender) {
		super(description);
		this.kind = kind;
		this.offender = offender;
	}
	
	public String toString() {
		return super.toString() + " @ " + offender.toString();
	}
}
