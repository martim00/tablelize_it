﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace tablelizeit_tests
{
    class Program
    {
        static void Main(string[] args)
        { 
            NUnit.ConsoleRunner.Runner.Main(new string[]
			{
				Assembly.GetExecutingAssembly().Location, 
			});
        }
    }
}
