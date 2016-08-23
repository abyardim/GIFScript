# GIFScript
A tool for creating GIFs by writing JavaScript code. The core tool provides the user with high level vector graphics functions, tweens and tranformations on top of the Nashorn JavaScript engine. All functions are available through a command line tool, a simple GUI interface allows easy usage of the tool. Created as a part of GSoC 2016.

## Progress
The core vector graphics features are complete, with a flexible tweening system for animations. Extensibility features ("modules"), higher level mathematics helpers, and a small GUI client have also been implemented. The GIF encoder is fully functional, but can still be improved for efficiency.
The tool allows the development of external modules for extensibility, so more such libraries can be developed for use in different fields.

## Dependencies
Both parts of the project require Java 1.8. The GUI tool uses [RichTextFX](https://github.com/TomasMikula/RichTextFX) for text editing.

## Building GIFScript
The source is distributed as an Eclipse project. Simple steps to build the tools:
1. Download the source from GitHub or clone the repository: `git clone https://github.com/abyardim/GIFScript`
2. Import the projects into your workspace, from File>Import>General>Existing projects into workspace
3. Download [richtextfx-fat-0.6.10](https://github.com/TomasMikula/RichTextFX/releases/tag/v0.6.10) and add the jar your classpath through Project properties>Java Build Path>Add External JARs.
4. Build/run either project.

The memory usage of the tool can rise for larger GIFs, so consider increasing the maximum Java VM heap size using the option `-Xms` when necessary.

## GIFScript modules
To build your own modules, you should put all your JS files in a directory along with a file named `init`. The first line of the init file should be `%name-of-your-module`, the later lines the JS file names in order of interpretation. Either pack all files into a ZIP or alternatively, you can run the command line tool with the arguments `-p path-to-init-file`. The ZIP file should have extension `gsc`.

## License
The core tool is available under the MIT license. The GUI tool in GIFScriptUI is provided under the GNU license. See [LICENSE](https://github.com/abyardim/GIFScript/blob/master/GIFScript/LICENSE).

## Examples
Some examples of GIFScript code can be found in the repository, under [/examples](https://github.com/abyardim/GIFScript/tree/master/GIFScript/examples), also published on my [GSoC blog](http://gifscript.blogspot.com/).

![cube](https://1.bp.blogspot.com/-1iCqFAqo29A/V0TWJbHLvLI/AAAAAAAAAAk/dHDP1qhTII06W8w0JbS24UZebUdRiGSsACLcB/s1600/cube-reduced.gif "Cube")
![TSP](https://4.bp.blogspot.com/-V06vQMbOJD4/V3GiKMhj2JI/AAAAAAAAABE/W8o--hFKyo46Jny1-5Txu4B1UoIqokgAwCKgB/s1600/turkey_TSP.gif "TSP")
![dudeney](http://i.imgur.com/aQUvSox.gif "Dudeney dissection")
![Fourier](http://i.imgur.com/Z0iXz01.gif "Fourier series vs. Fourier transform")

